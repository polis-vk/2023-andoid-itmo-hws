package ru.ok.itmo.example

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment(R.layout.login_fragment) {
    private var savedLogin: String? = null
    private var savedPassword: String? = null

    companion object {
        const val LOGIN = "login"
        const val PASSWORD = "password"
        const val correctLogin = "login"
        const val correctPassword = "login"
    }

    //private lateinit var loginViewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

//        loginViewModel.loginResult.observe(viewLifecycleOwner) { result ->
//
//            if (result.isSuccess) {
//                val successMessage =  "Успех"
//                Toast.makeText(requireContext(), successMessage, Toast.LENGTH_SHORT).show()
//
//            } else {
//                val errorMessage =  "Неизвестная ошибка"
//                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
//
//            }
//
//        }
//
//
//
//        val login = view.findViewById<TextInputLayout>(R.id.login_input)
//        val password = view.findViewById<TextInputLayout>(R.id.password_input)
//        val signIn = view.findViewById<Button>(R.id.sign_in_button)
//
//        signIn.setOnClickListener {
//            val username = login.editText!!.text.toString()
//            val pwd = password.editText!!.text.toString()
//            loginViewModel.checkLogin(User(username, pwd))
//        }



//        if (savedInstanceState != null) {
//            savedLogin = savedInstanceState.getString(LOGIN)
//            savedPassword = savedInstanceState.getString(PASSWORD)
//            login.editText?.setText(savedLogin)
//            password.editText?.setText(savedPassword)
//        }
//
//        signIn.setOnClickListener {
//            savedLogin = login.editText!!.text.toString()
//            savedPassword = password.editText!!.text.toString()
//            if (login.editText!!.text.toString() == correctLogin &&
//                password.editText!!.text.toString() == correctPassword
//            ) {
//
//                parentFragmentManager.beginTransaction()
//                    .remove(this)
//                    .replace(R.id.screen_container, HomeFragment())
//                    .addToBackStack("home")
//                    .commit()
//            } else {
//                Toast.makeText(requireContext(), R.string.incorrect_input, Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//        }
//
//        password.editText!!.setOnEditorActionListener { _, _, event ->
//            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
//                signIn.performClick()
//                return@setOnEditorActionListener true
//            }
//            false
//        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LOGIN, savedLogin)
        outState.putString(PASSWORD, savedPassword)
    }
}
