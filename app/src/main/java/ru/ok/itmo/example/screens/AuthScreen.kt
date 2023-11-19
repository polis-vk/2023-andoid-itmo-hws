package ru.ok.itmo.example.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import ru.ok.itmo.example.R
import ru.ok.itmo.example.databinding.FragmentAuthScreenBinding


class AuthScreen : Fragment() {

    lateinit var binding: FragmentAuthScreenBinding

    companion object {
        const val correct_login = "admin"
        const val correct_password = "admin"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_authScreen_to_unloginScreen)
        }

        val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.submitButton.isEnabled = submitBtnState()
            }

            override fun afterTextChanged(s: Editable) {}
        }

        binding.loginTextInputEditText.addTextChangedListener(textWatcher)
        binding.passwordTextInputEditText.addTextChangedListener(textWatcher)

        binding.submitButton.setOnClickListener {
            if(binding.loginTextInputEditText.text.toString() == correct_login && binding.passwordTextInputEditText.text.toString() == correct_password){
                Navigation.findNavController(view).navigate(R.id.action_authScreen_to_homePageScreen)
            }else{
                Toast.makeText(requireContext(), "Не существует такого пользователя", Toast.LENGTH_SHORT).show()
            }
        }

        binding.forgotPassword.setOnClickListener{
            Toast.makeText(requireContext(), "В разработке", Toast.LENGTH_SHORT).show()
        }

    }

    fun submitBtnState() : Boolean{
        return !(binding.loginTextInputEditText.text.isNullOrBlank() || binding.passwordTextInputEditText.text.isNullOrBlank())
    }

}