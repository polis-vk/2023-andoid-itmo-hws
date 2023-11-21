package ru.ok.itmo.example.login

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ok.itmo.example.Helper
import ru.ok.itmo.example.R
import ru.ok.itmo.example.SharedViewModel
import ru.ok.itmo.example.server.ServerException

class FragmentLogin : Fragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels(ownerProducer = { requireActivity() })

    private lateinit var editTextLogin: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var btnLogin: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextLogin = view.findViewById(R.id.edit_text_login)
        editTextPassword = view.findViewById(R.id.edit_text_password)
        btnLogin = view.findViewById(R.id.btn_login)

        actionBarLogic()
        keyboardLogic()
        editTextLoginLogic()
        editTextPasswordLogic()
        btnLoginLogic()
        authorizationLogic()
    }

    private fun authorizationLogic() {
        val tokenObserver = Observer<String> { token ->
            successAuth(token)
        }
        viewModel.token.observe(viewLifecycleOwner, tokenObserver)
    }

    private fun btnLoginLogic() {
        val enableObserver = Observer<Boolean> { state ->
            btnLogin.isEnabled = state
            Helper.setButtonColor(
                btnLogin, requireContext(), if (state) R.color.buttons else R.color.disabled_button
            )
        }
        viewModel.readyForAuthorization.observe(viewLifecycleOwner, enableObserver)

        btnLogin.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    viewModel.authorization()
                } catch (e: ServerException) {
                    when (e) {
                        ServerException.Unauthorized ->
                            errorUnauthorized()
                    }
                } catch (e: Exception) {
                    showToastInFragment(e.message ?: "Unknown error")
                }
            }
        }
    }

    private fun errorUnauthorized() {
        showToastInFragment(getString(R.string.error_unauthorized))
    }

    private fun successAuth(token: String) {
        sharedViewModel.login(token)
        showToastInFragment(getString(R.string.success_auth))
        findNavController().navigate(R.id.action_fragmentLogin_to_app_nav_graph)
    }

    private fun showToastInFragment(message: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun keyboardLogic() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun actionBarLogic() {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.action_bar_login_title)
        actionBar?.setBackgroundDrawable(
            ColorDrawable(
                Helper.getColor(
                    requireContext(), R.color.white
                )
            )
        )
        actionBar?.elevation = 0f
    }

    private fun editTextLoginLogic() {
        editTextLogin.addTextChangedListener { text ->
            viewModel.changeLogin(text.toString())
        }
    }

    private fun editTextPasswordLogic() {
        editTextPassword.addTextChangedListener { text ->
            viewModel.changePassword(text.toString())
        }

        editTextPassword.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                btnLogin.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}