package ru.ok.itmo.example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import ru.ok.itmo.example.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_startFragment)
        }

        binding.loginButton.setOnClickListener {
            viewModel.login(
                binding.loginInputText.text.toString(),
                binding.passwordInputText.text.toString()
            )
        }

        binding.loginInputText.addTextChangedListener {
            checkCorrectInput()
            shorOrHideError(null)
        }

        binding.passwordInputText.addTextChangedListener {
            checkCorrectInput()
            shorOrHideError(null)
        }

        viewModel.loginState.observe(viewLifecycleOwner) {
            when (val result = it) {
                is LoginState.Success -> {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_loginFragment_to_chatsFragment)
                    shorOrHideError(null)
                }

                is LoginState.Failure -> {
                    shorOrHideError(result.throwable)
                }

                else -> {}
            }
        }
    }

    private fun checkCorrectInput() {
        binding.loginButton.isEnabled = !(binding.loginInputText.text.isNullOrEmpty()
                || binding.passwordInputText.text.isNullOrEmpty())
    }

    private fun shorOrHideError(error: Throwable?) {
        when (error) {
            null -> binding.errorText.text = ""
            is ErrorType.Unknown -> binding.errorText.text =
                "Неизвестная ошибка"

            is ErrorType.InternetConnection -> binding.errorText.text =
                "Ошибка подключения к сети или серверу"

            is ErrorType.Unauthorized -> binding.errorText.text =
                "Ошибка авторизации: неверный логин или пароль"

            else -> binding.errorText.text = "Ошибка"
        }
    }
}