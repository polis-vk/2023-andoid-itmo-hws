package com.example.loginapp

import android.R.attr.password
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val usernameField = findViewById<EditText>(R.id.username);
        val passwordField = findViewById<EditText>(R.id.password);
        val loginButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.login_button);
        val showPasswordButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.show_password);
        val loginCheck = findViewById<TextView>(R.id.login_validation);
        val passwordCheck = findViewById<TextView>(R.id.password_validation);
        val lightTheme = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.light_theme);
        val darkTheme = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.dark_theme);
        val systemTheme = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.system_theme);
        var showFlag = false;

        systemTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }

        darkTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        lightTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        loginButton.setOnClickListener {
            if (usernameField.text.toString() == "NeverGonnaGive@You.Up" &&
                passwordField.text.toString() == "NeverGonnaLetYouDown"
            ) {
                Toast.makeText(
                    this,
                    "Login Successfull, Sussy Baka",
                    Toast.LENGTH_LONG
                ).show()
                //SusSound.start()
            }
            if (usernameField.text.toString() == "") {
                loginCheck.text = getString(R.string.no_username);
            } else if (android.util.Patterns.EMAIL_ADDRESS.matcher(usernameField.text.toString()).matches()){
                loginCheck.text = "";
            } else {
                loginCheck.text = getString(R.string.not_email);
            }

            if(passwordField.text.toString() == ""){
                passwordCheck.text = getString(R.string.no_password);
            } else if (passwordField.text.toString().length < 6){
                passwordCheck.text = getString(R.string.not_password);
            } else {
                passwordCheck.text = "";
            }

        }
        showPasswordButton.setOnClickListener {
            if (showFlag) {
                passwordField.transformationMethod = PasswordTransformationMethod.getInstance()
                showFlag = false
            } else {
                passwordField.transformationMethod = HideReturnsTransformationMethod.getInstance()
                showFlag = true
            }
        }
        passwordField.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                loginButton.performClick()
                return@OnKeyListener true
            }
            false
        })
    }


}