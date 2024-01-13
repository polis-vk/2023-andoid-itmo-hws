package ru.ok.itmo.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity) {
    val autorizationFragment = AutorizationFragment()
    val mainFragment = MainFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, autorizationFragment)
                .add(R.id.fragment_container, mainFragment)
                .hide(mainFragment)
                .commit()
            supportFragmentManager.setFragmentResultListener("enter", this) { key, bundle ->
                supportFragmentManager.beginTransaction()
                    .hide(autorizationFragment)
                    .show(mainFragment)
                    .commit()
            }
            supportFragmentManager.setFragmentResultListener("back", this) { key, bundle ->
                supportFragmentManager.beginTransaction()
                    .hide(mainFragment)
                    .show(autorizationFragment)
                    .commit()
            }
        }
    }
}