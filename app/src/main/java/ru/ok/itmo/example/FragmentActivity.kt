package ru.ok.itmo.example

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.navigation.NavigationBarView

class FragmentActivity : FragmentActivity(R.layout.activity_menu) {
    private var stack = mutableListOf<String>()
    private lateinit var navigationMenu: NavigationBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stack.add("A")

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, Fragment("A"), stack.last())
            .commit()

        navigationMenu = findViewById(R.id.menu_buttons)

        navigationMenu.setOnItemSelectedListener {
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

        val count = (3..5).random()
        if (count < 5) {
            navigationMenu.menu.removeItem(R.id.E)
        }
        if (count < 4) {
            navigationMenu.menu.removeItem(R.id.D)
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
        if (name in stack.map { it }) {
            stack.takeLastWhile { it != name }.forEach { _ ->
                supportFragmentManager.popBackStack()
            }
            stack = stack.dropLastWhile { it != name }.toMutableList()
        } else {
            navigate(name)
        }
    }

    private fun navigate(name: String) {
        val new = Fragment(name)
        val prev = supportFragmentManager.findFragmentByTag(stack.last())
            ?: throw IllegalArgumentException("Fragment not found $name")

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, new, name)
            .hide(prev)
            .addToBackStack(name)
            .commit()

        stack.add(name)
    }

    override fun onBackPressed() {
        if (stack.size > 1) {
            supportFragmentManager.popBackStack()
            stack.removeLast()
            navigationMenu.selectedItemId = menuIndex(stack.last())
        } else {
            finish()
        }
    }
}