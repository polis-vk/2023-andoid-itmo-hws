package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            replaceFragment(Task1Fragment())
        }

        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            replaceFragment(Task1Fragment())
        }

        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            replaceFragment(Task2Fragment())
        }

        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener {
            replaceFragment(Task3Fragment())
        }

        val button4 = findViewById<Button>(R.id.button4)
        button4.setOnClickListener {
            replaceFragment(Task4Fragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
