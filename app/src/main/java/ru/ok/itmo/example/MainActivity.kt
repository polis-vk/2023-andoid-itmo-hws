package ru.ok.itmo.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ru.ok.itmo.example.alarm.AlarmFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(
                    R.id.fragment_container,
                    getFragment(
                        App.ALARM
                    )
                )
            }
        }
    }

    private enum class App {
        ALARM,
        PROGRESS_BAR
    }

    private fun getFragment(app: App): Fragment {
        return when (app) {
            App.ALARM -> AlarmFragment()
            App.PROGRESS_BAR -> MainFragment()
        }
    }
}