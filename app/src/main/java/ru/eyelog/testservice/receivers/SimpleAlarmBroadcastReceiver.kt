package ru.eyelog.testservice.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.eyelog.testservice.R
import ru.eyelog.testservice.activity.MainActivity

private const val CHANNEL_ID = "SimpleAlarmBroadcastReceiver id"

class SimpleAlarmBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val localIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("TargetFragment", "SimpleAlarmFragment")
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            1,
            localIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Simple Alarm Title")
            .setContentText("Simple Alarm Body")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(101, builder.build())
    }
}