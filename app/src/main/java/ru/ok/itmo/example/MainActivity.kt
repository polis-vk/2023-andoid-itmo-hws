package ru.ok.itmo.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

const val HOME = "home"
const val NAVIGATION = "navigation"

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction().hide(it).commit()
        }

        if (savedInstanceState == null) {
            val home = HomeFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, home, HOME)
                .commit()
        }
    }

    override fun onBackPressed() {
        val nav = supportFragmentManager.findFragmentByTag(NAVIGATION)

        when {
            nav != null && (nav as NavigationFragment).popBackStack() -> return
            supportFragmentManager.backStackEntryCount > 0 -> supportFragmentManager.popBackStack()
            else -> finish()
        }
    }
}
