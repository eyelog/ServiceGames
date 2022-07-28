package ru.eyelog.testservice.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.content.getSystemService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ru.eyelog.testservice.R
import java.util.concurrent.TimeUnit

private const val CHANNEL_ID = "Foreground service id"

class CustomForegroundService: Service() {

//    private val customBinder = CustomForegroundBinder()
    private var messageDisposable: Disposable? = null
    private var messageCounter = 0

    override fun onCreate() {
        Log.i("Logcat", "Service onCreate")
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.i("Logcat", "Service onBind")
//        return customBinder
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Logcat", "Service started")

        setCustomMessage("Start")

        messageDisposable = Observable.interval(1000L, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                messageCounter++
                setCustomMessage("Message $messageCounter")
            }

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_ID,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Service title")
            .setContentText("Service text")
            .setSmallIcon(R.drawable.ic_menu_list)

        startForeground(1001, notification.build())

        return START_STICKY
    }

//    override fun onUnbind(intent: Intent?): Boolean {
//        Log.i("Logcat", "Service onUnbind")
//        return super.onUnbind(intent)
//    }

    private fun setCustomMessage(value: String){
        val sendIntent = Intent()
        sendIntent.action = "ru.eyelog.testservice.presenter.foreground"
        sendIntent.putExtra("EXTRA", "Some $value message from service");
        sendBroadcast(sendIntent)
    }

//    fun setMessageCounter(value: Int) {
//        Log.i("Logcat", "setCustomMessage")
//        messageCounter = value
//    }

    override fun onDestroy() {
        Log.i("Logcat", "Service destroyed")
        messageDisposable?.dispose()
        super.onDestroy()
    }

//    inner class CustomForegroundBinder: Binder(){
//        fun getService(): CustomForegroundService = this@CustomForegroundService
//    }
}