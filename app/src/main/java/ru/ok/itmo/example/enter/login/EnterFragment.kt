package ru.ok.itmo.example.enter.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import ru.ok.itmo.example.R
import ru.ok.itmo.example.databinding.FragmentEnterBinding

class EnterFragment : Fragment(R.layout.fragment_enter) {
    companion object {
        const val TAG = "EnterFragment"
    }

    private var _binding: FragmentEnterBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<EnterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.isCorrectData.observe(viewLifecycleOwner) { isCorrectData ->
            binding.enterButton.isEnabled = isCorrectData
        }
    }

    private fun initViews() {
        binding.toolbar.setupWithNavController(findNavController())
        binding.loginField.editText?.setText(viewModel.getLogin())
        binding.passwordField.editText?.setText(viewModel.getPassword())
        binding.loginField.editText?.setOnFocusChangeListener { _, hasFocus ->
            binding.loginField.hint = resources.getString(
                if (hasFocus) R.string.login
                else R.string.input_login
            )
        }
        binding.passwordField.editText?.setOnFocusChangeListener { _, hasFocus ->
            binding.passwordField.hint = resources.getString(
                if (hasFocus) R.string.password
                else R.string.input_password
            )
        }
        binding.loginField.editText?.addTextChangedListener {
            viewModel.loginChange(binding.loginField.editText?.text.toString())
        }
        binding.passwordField.editText?.addTextChangedListener {
            viewModel.passwordChange(binding.passwordField.editText?.text.toString())
        }
        binding.enterButton.setOnClickListener {
            viewModel.logIn(::handleLogInResult)
        }
    }

    private fun handleLogInResult(result: LogInResult) {
        if (result.isAuthorized) {
            result.token?.let {
                toChats(result.token)
                return
            }
            Log.d(TAG, "Error: Authorized but token is null.")
            return
        }
        if (result.errorType == LogInResult.ErrorType.NO_ERROR) return
        val errorResId = when (result.errorType) {
            LogInResult.ErrorType.INVALID_LOGIN_OR_PASSWORD -> R.string.invalid_login_or_password
            LogInResult.ErrorType.NO_INTERNET_CONNECTION -> R.string.no_internet_connection
            LogInResult.ErrorType.TIMED_OUT -> R.string.timed_out
            else -> R.string.server_error
        }
        binding.errorMessage.text = resources.getString(errorResId)
    }

    private fun toChats(token: String) {
        findNavController().navigate(
            EnterFragmentDirections.actionNavToFragmentChats(token)
        )
        viewModel.loginChange("")
        viewModel.passwordChange("")
    }
}