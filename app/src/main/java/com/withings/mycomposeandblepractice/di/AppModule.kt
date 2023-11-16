package com.withings.mycomposeandblepractice.di

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import androidx.work.WorkManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.withings.mycomposeandblepractice.data.local.AppDatabase
import com.withings.mycomposeandblepractice.data.local.ImageEntity
import com.withings.mycomposeandblepractice.data.local.SearchPageRepository
import com.withings.mycomposeandblepractice.data.remote.ImageRemoteMediator
import com.withings.mycomposeandblepractice.data.remote.PixabayApi
import com.withings.mycomposeandblepractice.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWorkManager(application: Application): WorkManager = WorkManager.getInstance(application)

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideSearchPageRepository(): SearchPageRepository {
        return SearchPageRepository()
    }

    /*@Singleton
    @Provides
    fun provideSensorManager(@ApplicationContext context: Context): SensorManager {
        return context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }*/

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideMyApi(): PixabayApi {
        return Retrofit.Builder()
            .baseUrl(PixabayApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Singleton
    @Provides
    fun provideImagePager(appDb: AppDatabase, pixabayApi: PixabayApi, searchPageRepository: SearchPageRepository): Pager<Int, ImageEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ImageRemoteMediator(
                appDatabase = appDb,
                pixabayApi = pixabayApi,
                searchPageRepository = searchPageRepository
            ),
            pagingSourceFactory = {
                appDb.imageDao.pagingSource()
            }
        )
    }

}