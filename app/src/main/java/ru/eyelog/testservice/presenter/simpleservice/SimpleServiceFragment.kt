package ru.eyelog.testservice.presenter.simpleservice

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_service.buttonStart
import kotlinx.android.synthetic.main.fragment_service.buttonStop
import kotlinx.android.synthetic.main.fragment_service.textView
import ru.eyelog.testservice.R
import ru.eyelog.testservice.services.CustomService

@AndroidEntryPoint
class SimpleServiceFragment: Fragment(), LifecycleOwner {

    private val customService = CustomService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_service, container, false)

        customService.lifeContainer.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        buttonStart.setOnClickListener {
            customService.startService(Intent(requireContext(), CustomService::class.java))
        }

        buttonStop.setOnClickListener {
            customService.stopSelf()
        }
    }
}