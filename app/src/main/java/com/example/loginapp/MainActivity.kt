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
        val usernameField: EditText = findViewById(R.id.username);
        val passwordField: EditText = findViewById(R.id.password);
        val loginButton: androidx.appcompat.widget.AppCompatButton = findViewById(R.id.login_button);
        val changeThemeSwitch: Switch = findViewById(R.id.theme_button);
        val showPasswordButton: androidx.appcompat.widget.AppCompatButton = findViewById(R.id.show_password);
        val loginCheck: TextView = findViewById(R.id.login_validation);
        val passwordCheck: TextView = findViewById(R.id.password_validation);
        var showFlag: Boolean = false;

        changeThemeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }

        loginButton.setOnClickListener {
            if (usernameField.getText().toString() == "NeverGonnaGive@You.Up" &&
                passwordField.getText().toString() == "NeverGonnaLetYouDown"
            ) {
                Toast.makeText(
                    this,
                    "Login Successfull, Sussy Baka",
                    Toast.LENGTH_LONG
                ).show()
                //SusSound.start()
            }
            if (usernameField.getText().toString() == "") {
                loginCheck.setText("No Username provided");
            } else if (android.util.Patterns.EMAIL_ADDRESS.matcher(usernameField.getText().toString()).matches()){
                loginCheck.setText("");
            } else {
                loginCheck.setText("Not an Email address, please enter a valid Email address");
            }

            if(passwordField.getText().toString() == ""){
                passwordCheck.setText("No password provided");
            } else if (passwordField.getText().toString().length < 6){
                passwordCheck.setText("Your password must be at least 6 characters long!");
            } else {
                passwordCheck.setText("");
            }

        }
        showPasswordButton.setOnClickListener {
            if (showFlag) {
                passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance())
                showFlag = false
            } else {
                passwordField.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                showFlag = true
            }
        }
        passwordField.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                loginButton.performClick()
                return@OnKeyListener true
            }
            false
        })
        /*usernameField.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                var s: String = p0.toString();
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    loginCheck.setText("");
                } else {
                    loginCheck.setText("Not an Email address, please enter a valid Email address");
                }
            }
        })

        passwordField.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                var s: String = p0.toString();
                if(s.length < 6){
                    passwordCheck.setText("Your password must be at least 6 characters long!");
                } else {
                    loginCheck.setText("Not an Email address, please enter a valid Email address");
                }
            }
        })*/

        /*changeThemeButton.setOnClickListener {
            if (darkThemeFlag){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                darkThemeFlag = false;
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                darkThemeFlag = true;
            }
        }*/
    }


}