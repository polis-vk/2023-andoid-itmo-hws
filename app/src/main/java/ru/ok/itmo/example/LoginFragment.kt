package ru.ok.itmo.example


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: MainViewModel by activityViewModels()
    private var button: Button? = null
    private var loginField : TextInputEditText? = null
    private var passwordField: EditText? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FragmentContainerView>(R.id.action_bar_container)
            .getFragment<SimpleActionBar>().title = getString(R.string.action_bar_login)

        button = view.findViewById<Button?>(R.id.buttonSignIn).apply {
            setOnClickListener {
                viewModel.verifyAndLogin(
                    loginField?.text.toString(),
                    passwordField?.text.toString())
            }
            isEnabled = false
        }

        loginField = view.findViewById<TextInputEditText?>(R.id.editTextLogin).apply {
            doOnTextChanged { _, _, _, _ ->
               enableButtonIfNeed()
            }
        }
        passwordField = view.findViewById<EditText?>(R.id.editTextPassword).apply {
            doOnTextChanged { _, _, _, _ ->
                enableButtonIfNeed()
            }
        }
    }

    private fun enableButtonIfNeed() {
        button?.isEnabled = ((loginField?.text?.isNotEmpty() ?: false) &&
                (passwordField?.text?.isNotEmpty() ?: false))
    }

    override fun onDestroyView() {
        button = null
        loginField = null
        passwordField = null
        super.onDestroyView()
    }
}