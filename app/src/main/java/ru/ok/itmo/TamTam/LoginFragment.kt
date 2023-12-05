package ru.ok.itmo.TamTam

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment(R.layout.login_fragment) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()

        loginViewModel.loginResult.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) {
                controller.navigate(R.id.chatsFragment)
            } else {
                Toast.makeText(requireContext(), R.string.incorrect_input, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val login = view.findViewById<TextInputEditText>(R.id.login)
        val password = view.findViewById<TextInputEditText>(R.id.password_input)
        val passwordLayout = view.findViewById<TextInputLayout>(R.id.password_layout)
        val signIn = view.findViewById<Button>(R.id.sign_in)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)



        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        signIn.setOnClickListener {
            val username = login.text.toString()
            val pwd = password.text.toString()
            loginViewModel.checkLogin(User(username, pwd))
        }

        passwordLayout.setEndIconOnClickListener {
            val isPasswordVisible =
                password.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            password.inputType =
                if (isPasswordVisible) InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                else InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            val iconResId =
                if (isPasswordVisible) {
                    R.drawable.ic_eye
                } else {
                    R.drawable.ic_inv_eye
                }
            passwordLayout.setEndIconDrawable(iconResId)
        }

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val isButtonEnabled = !login.text.isNullOrBlank() && !password.text.isNullOrBlank()
                signIn.isEnabled = isButtonEnabled
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        }

        login.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)
    }
}
