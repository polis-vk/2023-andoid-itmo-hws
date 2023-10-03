package ru.ok.itmo.example

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.snackbar.Snackbar
import ru.ok.itmo.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnter.setOnClickListener {

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            val emailError = binding.etEmail.text.toString().let { email -> when {
                email.isBlank() -> it.context.resources.getString(R.string.email_error_empty)
                !email.isEmail() -> it.context.resources.getString(R.string.email_error_incorrect)
                else -> null
            } }
            val passwordError = binding.etPassword.text.toString().let { password -> when {
                password.isBlank() -> it.context.resources.getString(R.string.password_error_empty)
                !password.checkPassword() -> it.context.resources.getString(R.string.password_error_incorrect)
                else -> null
            } }
            val snackbar = Snackbar.make(binding.root, R.string.entering, Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction(R.string.cancel) { snackbar.dismiss() }

            with(binding) {
                tilEmail.error = emailError
                tilPassword.error = passwordError
                Log.d("Main til email", tilEmail.isErrorEnabled.toString())
                Log.d("Main til password", tilPassword.isErrorEnabled.toString())
                if(emailError == null && passwordError == null) {
                    snackbar.show()
                }
            }

        }

        binding.etPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.btnEnter.performClick()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.btnSwitchTheme.setOnClickListener {

            when(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.btnAutoTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }



    }


}