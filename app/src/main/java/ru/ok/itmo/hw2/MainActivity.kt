package ru.ok.itmo.hw2

import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial


class MainActivity() : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userPassword: EditText = findViewById(R.id.user_password)
        val buttonEnter: Button = findViewById(R.id.button_enter)
        val switchTheme: SwitchMaterial = findViewById(R.id.switch_theme)

        buttonEnter.setOnClickListener {
            tryToSignIn(userLogin.text.toString().trim(), userPassword.text.toString().trim())
        }

        userPassword.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KEYCODE_ENTER) {
                tryToSignIn(userLogin.text.toString().trim(), userPassword.text.toString().trim())
                return@setOnKeyListener true
            }
            false
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun tryToSignIn(login: String, password: String) {
        when {
            login.isEmpty() -> printMessage(getString(R.string.login_empty))
            password.isEmpty() -> printMessage(getString(R.string.password_empty))
            !isLoginCorrect(login) -> printMessage(getString(R.string.login_incorrect))
            !isPasswordCorrect(password) -> printMessage(getString(R.string.password_incorrect))
            else -> printMessage(getString(R.string.completed))
        }
//        if (login.isEmpty()) {
//            printMessage("Login filed is empty")
//        } else if (password.isEmpty()) {
//            printMessage("Password filed is empty")
//        } else if (!isLoginCorrect(login)) {
//            printMessage("Login uncorrected")
//        } else if (!isPasswordCorrect(password)) {
//            printMessage("Password uncorrected")
//        } else {
//            printMessage("Completed")
//        }
    }

    private fun printMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun isLoginCorrect(login: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(login).matches()
    }

    private fun isPasswordCorrect(password: String): Boolean {
        return password.length > 6 && password.lowercase() != password
                && password.uppercase() != password
    }
}