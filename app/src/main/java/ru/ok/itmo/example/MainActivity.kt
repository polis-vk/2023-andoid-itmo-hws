package ru.ok.itmo.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.activity.viewModels

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    //private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .add(R.id.screen_container, AuthFragment())
            .commit()
    }
}