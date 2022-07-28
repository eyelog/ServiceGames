package ru.eyelog.testservice.services

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class CustomSimpleService: Service() {

    private val customBinder = CustomSimpleBinder()
    private var messageDisposable: Disposable? = null
    private var messageCounter = 0

    override fun onCreate() {
        Log.i("Logcat", "Service onCreate")
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder {
        Log.i("Logcat", "Service onBind")
        return customBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Logcat", "Service started")
        setCustomMessage("Start")
        messageGenerator()
        return START_STICKY
    }

    private fun messageGenerator(){
        messageDisposable = Observable.interval(1L, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                messageCounter++
                setCustomMessage("Message $messageCounter")
            }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("Logcat", "Service onUnbind")
        messageDisposable?.dispose()
        return super.onUnbind(intent)
    }

    private fun setCustomMessage(value: String){
        val sendIntent = Intent()
        sendIntent.action = "ru.eyelog.testservice.presenter.simpleservice"
        sendIntent.putExtra("EXTRA", "Some $value message from service");
        sendBroadcast(sendIntent)
    }

    fun setMessageCounter(value: Int) {
        Log.i("Logcat", "setCustomMessage")
        messageCounter = value
    }

    override fun onDestroy() {
        Log.i("Logcat", "Service destroyed")
        messageDisposable?.dispose()
        super.onDestroy()
    }

    inner class CustomSimpleBinder: Binder(){
        fun getService(): CustomSimpleService = this@CustomSimpleService
    }
}