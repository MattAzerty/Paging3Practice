package com.withings.mycomposeandblepractice.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.withings.mycomposeandblepractice.BuildConfig
import com.withings.mycomposeandblepractice.data.local.AppDatabase
import com.withings.mycomposeandblepractice.data.local.SearchPageRepository
import com.withings.mycomposeandblepractice.data.local.ImageEntity
import com.withings.mycomposeandblepractice.data.mappers.toPictureEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@ExperimentalPagingApi
class ImageRemoteMediator @Inject constructor(
    private val pixabayApi: PixabayApi,
    private val appDatabase: AppDatabase,
    private val searchPageRepository: SearchPageRepository
) : RemoteMediator<Int, ImageEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        1
                    } else {
                        searchPageRepository.currentPageFlow.value + 1
                    }
                }
            }

            val pixabayResponse = pixabayApi.getImages(
                apiKey = BuildConfig.PIXABAY_API_KEY,
                query = searchPageRepository.searchFlow.value,
                page = loadKey,
                pageCount = state.config.pageSize,
            )

            appDatabase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    appDatabase.imageDao.clearAll()
                }
                val imageEntities = pixabayResponse.hits.map { it.toPictureEntity() }
                searchPageRepository.setNewPage()
                appDatabase.imageDao.upsertAll(imageEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached =
                (searchPageRepository.currentPageFlow.value * state.config.pageSize) > pixabayResponse.totalHits
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}