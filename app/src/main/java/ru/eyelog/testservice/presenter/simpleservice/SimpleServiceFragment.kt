package ru.eyelog.testservice.presenter.simpleservice

import android.content.*
import android.content.Context.BIND_AUTO_CREATE
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_service.*
import ru.eyelog.testservice.R
import ru.eyelog.testservice.services.CustomService

private const val BROADCAST_ACTION = "ru.eyelog.testservice.presenter.simpleservice"

@AndroidEntryPoint
class SimpleServiceFragment: Fragment() {

    private var serviceBound = false

    private val tickReceiver by lazy { makeBroadcastReceiver() }
    lateinit var serviceConnection: ServiceConnection
    lateinit var customService: CustomService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonStart.setOnClickListener {
            requireContext().startService(Intent(requireContext(), CustomService::class.java))
        }

        buttonStop.setOnClickListener {
            requireContext().unbindService(serviceConnection)
            requireContext().stopService(Intent(requireContext(), CustomService::class.java))
        }

        btToggleService.setOnClickListener {
            customService.setMessageCounter(100)
        }

        val intentFilter = IntentFilter(BROADCAST_ACTION)
        requireContext().registerReceiver(tickReceiver, intentFilter)

        val binderIntent = Intent(requireContext(), CustomService::class.java)
        serviceConnection = object : ServiceConnection{
            override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
                val binder = service as CustomService.CustomBinder
                customService = binder.getService()
                serviceBound = true
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                serviceBound = false
            }
        }

        requireContext().bindService(binderIntent, serviceConnection, BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            requireContext().unregisterReceiver(tickReceiver)
        } catch (e: IllegalArgumentException) {
            Log.e("Broadcast", "Time tick Receiver not registered", e)
        }
        requireContext().unbindService(serviceConnection)
        requireContext().stopService(Intent(requireContext(), CustomService::class.java))
    }

    private fun setMessage(value: String){
        tvTitle.text = value
    }

    private fun makeBroadcastReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.i("Logcat", "Service message received $intent")
                intent?.let {
                    intent.getStringExtra("EXTRA")?.let {
                        setMessage(it)
                    }
                }
            }
        }
    }
}