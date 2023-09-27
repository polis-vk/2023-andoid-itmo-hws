package ru.ok.itmo.example

import android.content.DialogInterface
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate


// проверил на паре эсмуляторов и на своём телефоне Redmi Note 8
class MainActivity : AppCompatActivity() {

    var etEmail: EditText? = null
    var etPassword: EditText? = null
    var themeButt : Button? = null
    //так как у editText есть id, то уже введённые данные при смене ориентации остаются. Вроде...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        val enter = fromScreenOrientation(findViewById<Button>(R.id.enter1), findViewById<Button>(R.id.enter2))
        etEmail = findViewById(R.id.username)
        etPassword = findViewById(R.id.password)
        val hide = findViewById<Button>(R.id.hide)
        themeButt = findViewById(R.id.theme)
        enter.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var allOk = CheckFields()
                if (allOk) {
                    Toast.makeText(applicationContext, "Успех", Toast.LENGTH_SHORT).show()
                }
            }
        })

        etPassword!!.setOnKeyListener { v, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    enter.performClick()
                    return@setOnKeyListener true
                }
                else -> false
            }
        }
        hide.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> etPassword!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    MotionEvent.ACTION_UP ->  etPassword!!.transformationMethod =  PasswordTransformationMethod.getInstance()
                }
                return v?.onTouchEvent(event) ?: true
            }
        })

        themeButt!!.setOnClickListener(View.OnClickListener {
            if(resources.getString(R.string.mode) == "Day"){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })


    }


    private fun fromScreenOrientation(btn1 : Button, btn2 : Button) : Button {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            btn2.visibility = View.GONE
            btn1.visibility = View.VISIBLE
            return btn1;
        } else {
            btn1.visibility = View.GONE
            btn2.visibility = View.VISIBLE
            return btn2;
        }
    }

    private fun CheckFields(): Boolean {
        if (etEmail!!.length() == 0) {
            etEmail!!.error = "This field is required"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail!!.text).matches()) {
            etEmail!!.error = "Not Email"
            return false
        }
        if (etPassword!!.length() == 0) {
            etPassword!!.error = "This field is required"
            return false
        }
        if (etPassword!!.length() < 6) {
            etPassword!!.error = "Password must be minimum 6 characters"
            return false
        }
        return true
    }
}