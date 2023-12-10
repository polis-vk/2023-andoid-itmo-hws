package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.ok.itmo.example.login.LoginViewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    private val loginViewModel by viewModels<LoginViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<AppCompatImageButton>(R.id.signOut).setOnClickListener {
            loginViewModel.logout()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }
    }
}