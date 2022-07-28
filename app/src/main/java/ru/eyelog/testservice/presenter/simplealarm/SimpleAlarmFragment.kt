package ru.eyelog.testservice.presenter.simplealarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_service.*
import ru.eyelog.testservice.R
import ru.eyelog.testservice.receivers.CustomBroadcastReceiver
import ru.eyelog.testservice.receivers.SimpleAlarmBroadcastReceiver

private const val CHANNEL_ID = "SimpleAlarmBroadcastReceiver id"

class SimpleAlarmFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val notifyIntent = Intent(requireContext(), SimpleAlarmBroadcastReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(
            requireContext(),
            4,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_ID,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        requireContext().getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        buttonStart.setOnClickListener {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 10000,
                alarmIntent
            )
        }

        buttonStop.setOnClickListener {
            alarmManager.cancel(alarmIntent)
        }
    }
}