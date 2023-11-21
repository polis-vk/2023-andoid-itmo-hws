package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class StartFragment : Fragment(R.layout.start_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val loginButton = view.findViewById<Button>(R.id.start_button)
        loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_start_fragment_to_login_fragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}