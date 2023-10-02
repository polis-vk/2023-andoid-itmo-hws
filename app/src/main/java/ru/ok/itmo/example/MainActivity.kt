package ru.ok.itmo.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate


// проверил на паре эсмуляторов и на своём телефоне Redmi Note 8
class MainActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText;
    private lateinit var etPassword: EditText
    private lateinit var themeButt: Button

    //так как у editText есть id, то уже введённые данные при смене ориентации остаются. Вроде...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        val enter = findViewById<Button>(R.id.enter)
        etEmail = findViewById(R.id.username)
        etPassword = findViewById(R.id.password)
        val visiblePassBtn = findViewById<Button>(R.id.visiblePassBtn)
        themeButt = findViewById(R.id.theme)
        enter.setOnClickListener {
            val allOk = checkFields()
            if (allOk) {
                Toast.makeText(applicationContext, getString(R.string.succes), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        etPassword.setOnKeyListener { v, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    enter.performClick()
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        visiblePassBtn.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> etPassword!!.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()

                MotionEvent.ACTION_UP -> etPassword!!.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
            v?.onTouchEvent(event) ?: true
        }

        themeButt.setOnClickListener {
            if (resources.getString(R.string.mode) == "Day") {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun checkFields(): Boolean {
        if (etEmail.length() == 0) {
            etEmail.error = getString(R.string.field_is_required)
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text).matches()) {
            etEmail.error = getString(R.string.not_email)
            return false
        }
        if (etPassword.length() == 0) {
            etPassword.error = getString(R.string.field_is_required)
            return false
        }
        if (etPassword.length() < 6) {
            etPassword.error = getString(R.string.min_pass)
            return false
        }
        return true
    }
}