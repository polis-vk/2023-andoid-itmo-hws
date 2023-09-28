package ru.ok.itmo.example

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        switchThemeLogic()
        editTextPasswordLogic()
        btnLoginLogic()
    }

    private fun switchThemeLogic()
    {
        val switchTheme = findViewById<SwitchCompat>(R.id.switch_theme)
        if (Utils.isNightModeActive(this)){
            switchTheme.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun editTextPasswordLogic()
    {
        val editTextPassword = findViewById<TextInputEditText>(R.id.edit_text_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        editTextPassword.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                btnLogin.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun btnLoginLogic()
    {
        val textInputLayoutEmail = findViewById<TextInputLayout>(R.id.text_input_layout_email)
        val textInputLayoutPassword = findViewById<TextInputLayout>(R.id.text_input_layout_password)
        val editTextEmail = findViewById<TextInputEditText>(R.id.edit_text_email)
        val editTextPassword = findViewById<TextInputEditText>(R.id.edit_text_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        btnLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty()) {
                textInputLayoutEmail.error = getString(R.string.error_empty_email)
                textInputLayoutPassword.error = null
            } else if (!isValidEmail(email)) {
                textInputLayoutEmail.error = getString(R.string.error_incorrect_email)
                textInputLayoutPassword.error = null
            } else if (password.isEmpty()) {
                textInputLayoutEmail.error = null
                textInputLayoutPassword.error = getString(R.string.error_empty_password)
            } else if (password.length < 6) {
                textInputLayoutEmail.error = null
                textInputLayoutPassword.error = getString(R.string.error_less_than_6_characters_password)
            } else {
                val userId = authorize(email, password)

                if (userId == null) {
                    textInputLayoutEmail.error = null
                    textInputLayoutPassword.error = getString(R.string.error_incorrect_password)
                } else {
                    setResult(RESULT_OK, Intent().apply {
                        putExtra(LoginResultContract.RESULT_KEY, LoginData(userId))
                    })
                    finish()
                }
            }
        }
    }

    @Suppress("UNUSED_PARAMETER", "RedundantNullableReturnType")
    private fun authorize(login: String, password: String): String?
    {
        /** MAGIC **/
        return "${login}+${42}"
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
