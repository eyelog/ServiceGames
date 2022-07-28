package ru.eyelog.testservice.services

import android.R
import android.app.*
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.eyelog.testservice.activity.MainActivity
import java.util.concurrent.TimeUnit


private const val CHANNEL_ID = "CustomIntentService id"

class CustomIntentService() : IntentService("CustomIntentService") {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Logcat", "Service started")

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_ID,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Intent Service title")
            .setContentText("Intent Service text")
            .setSmallIcon(ru.eyelog.testservice.R.drawable.ic_menu_list)

        startForeground(1002, notification.build())

        return START_STICKY
    }

    override fun onHandleIntent(p0: Intent?) {

        Log.i("Logcat", "CustomIntentService started")

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_ID,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("My Title")
            .setContentText("This is the Body")
            .setSmallIcon(R.drawable.ic_menu_camera)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notifyIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            2,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        builder.setContentIntent(pendingIntent)
        val notificationCompat = builder.build()
        val managerCompat = NotificationManagerCompat.from(this)
        managerCompat.notify(3, notificationCompat);
    }
}