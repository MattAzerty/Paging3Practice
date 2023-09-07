package com.withings.mycomposeandblepractice.data.local


import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.withings.mycomposeandblepractice.MainActivity
import com.withings.mycomposeandblepractice.R
import com.withings.mycomposeandblepractice.di.CoroutineDispatcherProvider
import com.withings.mycomposeandblepractice.utils.CHANNEL_ID
import com.withings.mycomposeandblepractice.utils.KEY_INPUT_DATA_WORK_MANAGER_REF_PIC_LIST
import com.withings.mycomposeandblepractice.utils.fromJson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext


@HiltWorker
class DownloadPictureWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val gson: Gson,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val storageService: StorageService,
) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result = withContext(coroutineDispatcherProvider.io) {

        val imageEntitiesAsJson = inputData.getString(KEY_INPUT_DATA_WORK_MANAGER_REF_PIC_LIST)

        if (imageEntitiesAsJson != null) {

            val imageEntities = gson.fromJson<List<ImageEntity>>(json = imageEntitiesAsJson)

            storageService.storeEstatePictureEntities(imageEntities!!)

            sendNotification(context)

        } else {
            Log.e(javaClass.simpleName, "Failed to get data with key $KEY_INPUT_DATA_WORK_MANAGER_REF_PIC_LIST from data: $inputData")
            Result.failure()
        }

    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(
        context: Context,
    ): Result {


        val downloadCompleteString = applicationContext.getString(R.string.download_complete)
        val tapToOpenFolderString = applicationContext.getString(R.string.tap_to_open_folder)

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, "Default", importance)
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

            // Create an explicit intent for an Activity in your app
            val intent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP// set intent so it does not start a new activity
            }

            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_file_download_24dp)
                .setContentTitle(downloadCompleteString)
                .setContentText(tapToOpenFolderString)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)


            with(NotificationManagerCompat.from(context)) {
                notify(1, builder.build())
            }

        return Result.success()
    }
}