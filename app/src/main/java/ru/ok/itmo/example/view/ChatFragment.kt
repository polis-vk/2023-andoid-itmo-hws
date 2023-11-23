package ru.ok.itmo.example.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.ok.itmo.example.R

class ChatFragment : Fragment(R.layout.fragment_chat) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.simpleActionBar!!.title = requireArguments().getString(ARG_CHANNEL_NAME)!!
    }

    companion object {
        const val ARG_CHANNEL_NAME = "channel_name"
    }
}