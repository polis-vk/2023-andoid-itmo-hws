package ru.ok.itmo.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ok.itmo.example.R
import ru.ok.itmo.weatherapp.ui.MainFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commit()
        }
    }
}