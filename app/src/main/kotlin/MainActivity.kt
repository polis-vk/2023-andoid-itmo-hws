package itmo.hw

import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var signInButton: Button;
    private lateinit var switchThemeButton: Button;
    private lateinit var login: TextInputLayout;
    private lateinit var password: TextInputLayout;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signInButton = findViewById(R.id.sign_in_button)
        switchThemeButton = findViewById(R.id.switch_theme_button)
        login = findViewById(R.id.login_input)
        password = findViewById(R.id.password_input)

        signInButton.setOnClickListener {
            signInButtonClick()
        }

        switchThemeButton.setOnClickListener {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        password.editText?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                signInButtonClick()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun signInButtonClick() {
        if (login.editText?.text?.isEmpty() == true) {
            Toast.makeText(applicationContext, R.string.login_empty_toast, Toast.LENGTH_SHORT).show()
        } else if (password.editText?.text?.isEmpty() == true) {
            Toast.makeText(applicationContext, R.string.password_empty_toast, Toast.LENGTH_SHORT).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(login.editText?.text ?: "").matches()) {
            Toast.makeText(applicationContext, R.string.incorrect_email_toast, Toast.LENGTH_SHORT).show()
        } else if (password.editText!!.text.length < 6) {
            Toast.makeText(applicationContext, R.string.incorrect_password_toast, Toast.LENGTH_SHORT).show()
        }
    }
}