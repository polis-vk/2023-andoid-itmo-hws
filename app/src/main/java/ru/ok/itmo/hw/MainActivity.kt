package ru.ok.itmo.hw

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.text.isDigitsOnly
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var currentNightMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "onCreate")
        currentNightMode = savedInstanceState?.getBoolean("currentNightMode") ?: isNightMode()
        setMode()

        val loginButton = findViewById<Button>(R.id.login_button)
        val themeSwitch = findViewById<FloatingActionButton>(R.id.theme_switch)
        val loginField = findViewById<EditText>(R.id.login)
        val passwordField = findViewById<EditText>(R.id.password)
        val errorText = findViewById<TextView>(R.id.error)
        with(themeSwitch) {
            setImageResource(if (currentNightMode) R.drawable.light_mode else R.drawable.dark_mode)
            setOnClickListener {
                currentNightMode = !currentNightMode
                setMode()
            }
        }
        with(passwordField) {
            setOnEditorActionListener { _, actionId, event ->
                if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    loginButton.performClick()
                }
                false
            }
        }
        with(loginButton) {
            setOnClickListener {
                val login = loginField.text.toString()
                val password = passwordField.text.toString()
                errorText.text = buildString {
                    if (login.isBlank()) {
                        append(getString(R.string.empty_login) + "\n")
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
                        append(getString(R.string.invalid_login) + "\n")
                    }
                    if (password.isBlank()) {
                        append(getString(R.string.empty_password) + "\n")
                    } else if (password.isDigitsOnly()) {
                        append(getString(R.string.digits_password) + "\n")
                    } else if (password.length < 8) {
                        append(getString(R.string.short_password) + "\n")
                    }
                }.also { if (it == "") Toast.makeText(context, getString(R.string.welcome), Toast.LENGTH_LONG).show() }
            }
        }
    }

    private fun setMode() {
        if (currentNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MainActivity", "onSaveInstanceState")
        outState.putBoolean("currentNightMode", currentNightMode)
    }

    private fun isNightMode(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) resources.configuration.isNightModeActive else false
}