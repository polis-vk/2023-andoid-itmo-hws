package ru.ok.itmo.example

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.navigation.NavigationBarView

class NavigationActivity : FragmentActivity(R.layout.activity_navigation) {
    private class FragmentInfo(val name: String, val backstack: Int) {
        override fun toString() = "$name:$backstack"
    }

    private var stack = mutableListOf<FragmentInfo>()
    private lateinit var navigation: NavigationBarView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stack += FragmentInfo("A", 1)

        supportFragmentManager.beginTransaction()
            .add(R.id.screen_container, ComponentFragment("A", 1, ::navigate), "${stack.last()}")
            .commit()

        navigation = findViewById(R.id.navigation_bar)

        navigation.setOnItemSelectedListener {
            createOrRollback(when (it.itemId) {
                R.id.A -> "A"
                R.id.B -> "B"
                R.id.C -> "C"
                R.id.D -> "D"
                R.id.E -> "E"
                else -> return@setOnItemSelectedListener false
            })

            true
        }

        navigation.menu.run {
            val count = (3..5).random()
            if (count < 5) {
                removeItem(R.id.E)
            }
            if (count < 4) {
                removeItem(R.id.D)
            }
        }
    }

    private fun menuIndex(name: String) = when (name) {
        "A" -> R.id.A
        "B" -> R.id.B
        "C" -> R.id.C
        "D" -> R.id.D
        "E" -> R.id.E
        else -> throw IllegalArgumentException("Unknown menu item $name")
    }

    private fun createOrRollback(name: String) {
        if (name in stack.map { it.name }) {
            stack.takeLastWhile { it.name != name }.forEach { _ ->
                supportFragmentManager.popBackStack()
            }
            stack = stack.dropLastWhile { it.name != name }.toMutableList()
        } else {
            navigate(name)
        }
    }

    private fun navigate(name: String) {
        val backstack = stack.count { it.name == name }

        val info = FragmentInfo(name, backstack + 1)
        val new = ComponentFragment(info.name, info.backstack, ::navigate)
        val prev = supportFragmentManager.findFragmentByTag("${stack.last()}")
            ?: throw IllegalArgumentException("Fragment not found ${stack.last()}")

        supportFragmentManager.beginTransaction()
            .add(R.id.screen_container, new, "$info")
            .hide(prev)
            .addToBackStack("$info")
            .commit()

        stack += info
    }

    override fun onBackPressed() {
        if (stack.size == 1) {
            finish()
            return
        }

        supportFragmentManager.popBackStack()
        stack.removeLast()
        navigation.selectedItemId = menuIndex(stack.last().name)
    }
}
