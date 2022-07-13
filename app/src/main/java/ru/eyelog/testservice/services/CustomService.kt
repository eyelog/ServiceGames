package ru.eyelog.testservice.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class CustomService: Service() {

    lateinit var lifeContainer: MutableLiveData<String>

    private var serviceDisposable: Disposable? = null

    private val observable = Observable.interval(100L, TimeUnit.MILLISECONDS)

    override fun onCreate() {
        super.onCreate()
        lifeContainer = MutableLiveData()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.i("Logcat", "Service started")
        serviceDisposable = observable
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                lifeContainer.postValue("some post")
            }

        return START_STICKY
    }

    override fun onDestroy() {
        Log.i("Logcat", "Service destroyed")

        serviceDisposable?.dispose()
        super.onDestroy()
    }
}