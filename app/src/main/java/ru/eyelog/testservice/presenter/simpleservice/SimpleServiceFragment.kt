package ru.eyelog.testservice.presenter.simpleservice

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_service.*
import ru.eyelog.testservice.R
import ru.eyelog.testservice.services.CustomService

@AndroidEntryPoint
class SimpleServiceFragment: Fragment() {

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
            requireContext().stopService(Intent(requireContext(), CustomService::class.java))
        }
    }

    private fun setMessage(value: String){
        tvTitle.text = value
    }
}