package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment

class LoginFragment : Fragment(R.layout.login_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val password = view.findViewById<EditText>(R.id.password)
        val login = view.findViewById<EditText>(R.id.login)
        val button = view.findViewById<Button>(R.id.login_enter)
        password.addTextChangedListener {
            button.isEnabled = !(password.text.toString() == "" || login.text.toString() == "")
        }
        login.addTextChangedListener {
            button.isEnabled = !(password.text.toString() == "" || login.text.toString() == "")
        }
        button.setOnClickListener {
            val passwordText: String  = password.text.toString()
            val loginText: String  = login.text.toString()
            if (correctCheck(loginText, passwordText) && loginIn(loginText, passwordText)) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .hide(this)
                    .add(R.id.fragment_container, MainFragment())
                    .commit()
            } else {
                Toast.makeText(requireActivity(), "Incorrect password or login", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun correctCheck(login: String, password: String): Boolean {
        if (login.isEmpty()) {
            return false
        } else if (password.isEmpty()) {
            return false
        } else if (!loginIsCorrect(login)) {
            return false
        } else return passwordIsCorrect(password)
    }

    private fun loginIsCorrect(login: String): Boolean {
        val indexPoint = login.indexOf('.')
        val indexDog = login.indexOf('@')
        return login != "" && indexDog != -1 && indexPoint != 1
                && indexDog > 1 && indexPoint > indexDog + 1
    }

    private fun passwordIsCorrect(password: String): Boolean {
        return password.length > 6 && password.lowercase() != password
                && password.uppercase() != password
    }

    private fun loginIn(login: String, password: String): Boolean {
        // обращение к серверу
        return login == "test@mial.com" && password == "1234567Aa"
    }
}