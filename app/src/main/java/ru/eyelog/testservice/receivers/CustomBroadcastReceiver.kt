package ru.eyelog.testservice.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.eyelog.testservice.services.CustomIntentService

class CustomBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val intentService = Intent(context, CustomIntentService::class.java)
        context?.startForegroundService(intentService)
    }
}