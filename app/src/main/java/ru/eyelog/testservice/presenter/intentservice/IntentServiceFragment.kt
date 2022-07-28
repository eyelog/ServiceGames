package ru.eyelog.testservice.presenter.intentservice

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_service.*
import ru.eyelog.testservice.R
import ru.eyelog.testservice.receivers.CustomBroadcastReceiver
import ru.eyelog.testservice.services.CustomForegroundService
import ru.eyelog.testservice.services.CustomIntentService


class IntentServiceFragment: Fragment() {

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

        val notifyIntent = Intent(requireContext(), CustomBroadcastReceiver::class.java)

        val alarmIntent = PendingIntent.getBroadcast(
            requireContext(),
            3,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        buttonStart.setOnClickListener {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                10000,
                alarmIntent
            )
        }

        buttonStop.setOnClickListener {
            alarmManager.cancel(alarmIntent)
            requireContext().stopService(Intent(requireContext(), CustomIntentService::class.java))
        }
    }
}