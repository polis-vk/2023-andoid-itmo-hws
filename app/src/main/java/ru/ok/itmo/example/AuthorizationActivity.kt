package ru.ok.itmo.example

import android.os.Build
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


data class Model(var theme: Theme = Theme.LIGHT) {
    enum class Theme {
        LIGHT, DARK
    }
}

class AuthorizationActivity : AppCompatActivity(R.layout.activity_authorization) {

    private val model = Model()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (model.theme == Model.Theme.LIGHT) {
            outState.putBoolean("currentNightMode", false)
        } else {
            outState.putBoolean("currentNightMode", true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentNightMode = savedInstanceState?.getBoolean("currentNightMode")
            ?: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                resources.configuration.isNightModeActive
            else false

        if (!currentNightMode) {
            model.theme = Model.Theme.LIGHT
        } else {
            model.theme = Model.Theme.DARK
        }

        setTheme()

        val doneButton: Button = findViewById(R.id.done_button)
        val themeChangeButton: Button = findViewById(R.id.theme_button)
        val errorTextView: TextView = findViewById(R.id.error_view)
        val loginTextInput: EditText = findViewById(R.id.login_text_input)
        val passwordTextInput: EditText = findViewById(R.id.password_text_input)

        themeChangeButton.setOnClickListener {
            if (model.theme == Model.Theme.LIGHT) {
                model.theme = Model.Theme.DARK
            } else {
                model.theme = Model.Theme.LIGHT
            }
            setTheme()
        }

        passwordTextInput.setOnEditorActionListener { _, _, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                doneButton.performClick()
            }
            false
        }

        with(doneButton) {
            setOnClickListener {
                val login = loginTextInput.text.toString()
                val password = passwordTextInput.text.toString()
                var id: Int? = null
                if (login.isEmpty()) {
                    id = R.string.empty_login
                } else if (password.isEmpty()) {
                    id = R.string.empty_password
                } else if (EMAIL_ADDRESS.matcher(login).matches()) {
                    id = R.string.mail_expected
                } else if (password.length < 8) {
                    id = R.string.small_password
                }
                if (id == null) {
                    errorTextView.text = ""
                } else {
                    errorTextView.text = getString(id)
                }
            }
        }
    }

    private fun setTheme() {
        if (model.theme == Model.Theme.LIGHT) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

}