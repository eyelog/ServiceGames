package ru.eyelog.testservice.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.eyelog.testservice.R

private const val CHANNEL_ID = "SimpleAlarmBroadcastReceiver id"

class SimpleAlarmBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Simple Alarm Title")
            .setContentText("Simple Alarm Body")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(101, builder.build())
    }
}