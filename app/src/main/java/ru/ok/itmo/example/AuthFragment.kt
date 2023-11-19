package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


class AuthFragment : Fragment(R.layout.auth_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.enterByLogin).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.screen_container, LoginFragment())
                .addToBackStack("login")
                .commit()
        }

        view.findViewById<TextView>(R.id.enterByPhone).setOnClickListener {
            Toast.makeText(requireContext(), R.string.not_implemented, Toast.LENGTH_SHORT).show()
        }
    }
}