package ru.eyelog.testservice.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class CustomService: Service() {

    override fun onCreate() {
        Log.i("Logcat", "Service onCreate")
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Logcat", "Service started")

        return START_STICKY
    }

    override fun onDestroy() {
        Log.i("Logcat", "Service destroyed")

        super.onDestroy()
    }
}