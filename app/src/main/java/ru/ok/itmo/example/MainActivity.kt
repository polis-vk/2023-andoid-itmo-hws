package ru.ok.itmo.example

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private val email: EditText
        get() = findViewById(R.id.email)
    private val password: EditText
        get() = findViewById<TextInputLayout>(R.id.password).editText!!
    private val submit: Button
        get() = findViewById(R.id.submit)
    private var nightMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submit.setOnClickListener {
            when {
                email.text.isBlank() ->
                    Toast.makeText(this, R.string.noLogin, Toast.LENGTH_SHORT).show()
                password.text.isBlank() ->
                    Toast.makeText(this, R.string.noPassword, Toast.LENGTH_SHORT).show()
                !email.text.matches("^\\S+@\\S+\\.\\S+$".toRegex()) ->
                    Toast.makeText(this, R.string.notEmail, Toast.LENGTH_SHORT).show()
                password.text.length < 6 ->
                    Toast.makeText(this, R.string.shortPassword, Toast.LENGTH_SHORT).show()
            }
        }

        password.imeOptions = EditorInfo.IME_ACTION_GO

        password.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_GO -> submit.performClick()
                else -> false
            }
        }

        findViewById<Button>(R.id.nightModeButton).setOnClickListener {
            nightMode = !nightMode

            println(nightMode)

            AppCompatDelegate.setDefaultNightMode(if (nightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("nightMode", nightMode)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        nightMode = savedInstanceState.getBoolean("nightMode")
    }
}
