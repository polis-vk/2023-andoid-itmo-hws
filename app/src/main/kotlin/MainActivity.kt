package itmo.hw

import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    companion object {
        val TAG = MainActivity::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val signInButton = findViewById<Button>(R.id.sign_in_button)
        val switchThemeButton = findViewById<Button>(R.id.switch_theme_button)
        val login = findViewById<TextInputLayout>(R.id.login_input)
        val password = findViewById<TextInputLayout>(R.id.password_input)

        signInButton.setOnClickListener {
            if (login.editText?.text?.isEmpty() == true) {
                Toast.makeText(applicationContext, "Login field is empty", Toast.LENGTH_SHORT).show()
            } else if (password.editText?.text?.isEmpty() == true) {
                Toast.makeText(applicationContext, "Password field is empty", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(login.editText?.text ?: "").matches()) {
                Toast.makeText(applicationContext, "Incorrect email", Toast.LENGTH_SHORT).show()
            } else if (password.editText!!.text.length < 6) {
                Toast.makeText(applicationContext, "Incorrect password", Toast.LENGTH_SHORT).show()
            }
        }

        switchThemeButton.setOnClickListener {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        password.editText?.setOnKeyListener(View.OnKeyListener { _, _, event ->
            if (event?.action == KeyEvent.KEYCODE_ENTER) {
                signInButton.performClick()
                return@OnKeyListener true
            }
            return@OnKeyListener false
        })
    }
}