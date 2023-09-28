package ru.ok.itmo.hw2

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class MainActivity() : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userPassword: EditText = findViewById(R.id.user_password)
        val buttonEnter: Button = findViewById(R.id.button_enter)
        val switchTheme: Switch = findViewById(R.id.switch_theme)

        buttonEnter.setOnClickListener {
            signIn(userLogin.text.toString().trim(), userPassword.text.toString().trim())
        }

        userPassword.setOnKeyListener(
            fun(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                var consumed = false
                if (keyCode == KEYCODE_ENTER) {
                    signIn(userLogin.text.toString().trim(), userPassword.text.toString().trim())
                    consumed = true
                }
                return consumed
            }
        )

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


    }

    private fun signIn(login: String, password: String) {
        if (login == "") {
            printMessage("Login filed is empty")
        } else if (password == "") {
            printMessage("Password filed is empty")
        } else if (!loginIsCorrect(login)) {
            printMessage("Login uncorrected")
        } else if (!passwordIsCorrect(password)) {
            printMessage("Password uncorrected")
        } else {
            printMessage("Completed")
        }
    }

    private fun printMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun loginIsCorrect(login: String): Boolean {
        val indexPoint = login.indexOf('.')
        val indexDog = login.indexOf('@')
        return login != "" && indexDog != -1 && indexPoint != 1
                && indexDog > 1 && indexPoint > indexDog + 1
    }

    private fun passwordIsCorrect(password: String): Boolean {
        return password.length > 6 && password.lowercase() != password
                && password.uppercase() != password
    }
}