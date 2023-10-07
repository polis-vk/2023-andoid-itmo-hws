package ru.ok.itmo.example

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(R.layout.activity_main) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = findViewById<TextView>(R.id.email_input)
        val password = findViewById<TextView>(R.id.password_input)
        val enterButton = findViewById<Button>(R.id.button_enter)
        val showPasswordButton = findViewById<Button>(R.id.button_show_password)
        var isPasswordShowed = false

        enterButton.setOnClickListener {
            if (email.text.toString() == "" || email.text.toString().length < 6 ||
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                makeWidget("Wrong email")
            } else if (password.text.toString() == "") {
                makeWidget("Wrong password")
            } else {
                makeWidget("Log in successfully!")
            }
        }

        showPasswordButton.setOnClickListener {
            if  (isPasswordShowed) {
                password.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            isPasswordShowed = !isPasswordShowed
        }

    }


    private fun makeWidget(text: String) {
        val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.show();
    }


}