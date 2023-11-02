package ru.ok.itmo.example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContainerView = findViewById(R.id.fragmentContainerView)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.menu.add("Task 1")
        bottomNavigationView.menu.add("Task 2")
        bottomNavigationView.menu.add("Task 3")


        bottomNavigationView.setOnItemSelectedListener {
            setTaskFragment(it.title.toString())
            true
        }

        bottomNavigationView.selectedItemId = bottomNavigationView.menu.getItem(0).itemId

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }
    }

    private fun setTaskFragment(task: String) {
        val nextFragment = when (task) {
            "Task 1" -> Task1Fragment()
            "Task 2" -> Task2Fragment()
            "Task 3" -> Task3Fragment()
            else -> null
        }
        if (nextFragment == null) {
            Log.e("Not implemented task", "setTaskFragment: $task")
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, nextFragment)
                .commit()
        }
    }
}