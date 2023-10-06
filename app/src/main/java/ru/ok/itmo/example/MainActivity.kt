package ru.ok.itmo.example

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loginButton = findViewById<Button>(R.id.login_button)
        val textView = findViewById<TextView>(R.id.message)
        val emailForm = findViewById<TextInputEditText>(R.id.email_form)
        val passwordForm = findViewById<TextInputEditText>(R.id.password_form)
        val nightSwitch = findViewById<SwitchCompat>(R.id.night_switch)
        loginButton.setOnClickListener {
            val email = emailForm.text.toString()
            val password = passwordForm.text.toString()
            textView.setTextColor(Color.parseColor("#ff0000"))
            Log.println(Log.DEBUG, "debug", "'${email}' '${password}'")
            if (email == "") {
                textView.text = getString(R.string.no_email_error)
            } else if (password == "") {
                textView.text = getString(R.string.no_password_error)
            } else if (!email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}"))) {
                textView.text = getString(R.string.incorrect_email_error)
            } else if (password.length < 6) {
                textView.text = getString(R.string.incorrect_password_error)
            } else {
                textView.text = getString(R.string.correct_input)
                textView.setTextColor(Color.parseColor("#00ff00"))
            }
        }
        passwordForm.setOnKeyListener(View.OnKeyListener{_, key, event ->
            if (key == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                loginButton.performClick()
                return@OnKeyListener true
            }
            return@OnKeyListener false
        })
        if((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            nightSwitch.performClick()
        }
        nightSwitch.setOnCheckedChangeListener {_, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}