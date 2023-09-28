package ru.ok.itmo.example

import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial

import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    companion object {
        const val EMAIL_KEY: String = "email"
        const val PASSWORD_KEY: String = "password"
        const val VIEW_MODE_KEY: String = "view_mode"
    }
    private var email: String = ""
    private var password: String = ""
    private lateinit var emailField: TextInputLayout
    private lateinit var passwordField: TextInputLayout
    private lateinit var enterButton: Button
    private lateinit var nightModeSwitch: SwitchMaterial
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        enterButton = findViewById(R.id.enterButton)
        nightModeSwitch = findViewById(R.id.nightModeSwitch)
        passwordField.editText?.setOnEditorActionListener { _, actionId, keyEvent ->
            if ((keyEvent != null && keyEvent.action == KeyEvent.ACTION_UP &&
                keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) ||
                actionId == EditorInfo.IME_ACTION_DONE
            ) {
                clickEnter()
            }
            return@setOnEditorActionListener false
        }
        enterButton.setOnClickListener {
            clickEnter()
        }

        nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                toNightMode()
            } else {
                toDefaultMode()
            }
        }

        if (savedInstanceState != null) {
            emailField.editText?.setText(savedInstanceState.getString(EMAIL_KEY, ""))
            passwordField.editText?.setText(savedInstanceState.getString(PASSWORD_KEY, ""))
            nightModeSwitch.isChecked = savedInstanceState.getBoolean(VIEW_MODE_KEY, false)
        }
    }

    private fun toDefaultMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun toNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EMAIL_KEY, emailField.editText?.text.toString())
        outState.putString(PASSWORD_KEY, passwordField.editText?.text.toString())
        outState.putBoolean(VIEW_MODE_KEY, nightModeSwitch.isChecked)
    }

    private fun clickEnter() {
        emailField.isErrorEnabled = false
        passwordField.isErrorEnabled = false
        email = emailField.editText?.text.toString().trim()
        password = passwordField.editText?.text.toString().trim()
        val emailRes = validateEmail()
        val passwordRes = validatePassword()
        if (emailRes.hasError) {
            emailField.error = emailRes.errorMessage
        }
        if (passwordRes.hasError) {
            passwordField.error = passwordRes.errorMessage
        }
        if (passwordRes.hasError || emailRes.hasError) {
            return
        }
        println("You successfully sign in!")
    }
    private fun validateEmail(): ValidationResult {
        val blankTest = checkForBlank(email)
        if (blankTest.hasError)
            return blankTest

        if (!email.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            return ValidationResult(true, resources.getString(R.string.incorrect_email))
        }
        return ValidationResult.OK
    }

    private fun validatePassword(): ValidationResult {
        val blankTest = checkForBlank(password)
        if (blankTest.hasError)
            return blankTest
        if (password.length <= 8) {
            return ValidationResult(true, resources.getString(R.string.small_password))
        }
        return ValidationResult.OK
    }

    private fun checkForBlank(str: String): ValidationResult {
        if (str.isBlank()) {
            return ValidationResult(true, resources.getString(R.string.field_not_empty))
        }
        return ValidationResult.OK
    }
}