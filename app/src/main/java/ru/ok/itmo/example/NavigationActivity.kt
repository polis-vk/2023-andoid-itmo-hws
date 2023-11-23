package ru.ok.itmo.example

import android.os.Bundle
import androidx.core.os.bundleOf
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
            .add(R.id.screen_container,
                ComponentFragment::class.java,
                bundleOf(
                    "name" to "A",
                    "backstack" to 1
                ),
                "${stack.last()}")
            .commit()

        navigation = findViewById(R.id.navigation_bar)

        navigation.setOnItemSelectedListener {
            createOrRollback(
                when (it.itemId) {
                    R.id.menu_item_a -> "A"
                    R.id.menu_item_b -> "B"
                    R.id.menu_item_c -> "C"
                    R.id.menu_item_d -> "D"
                    R.id.menu_item_e -> "E"
                    else -> return@setOnItemSelectedListener false
                }
            )

            true
        }

        navigation.menu.run {
            val count = (SMALL_NAV_BAR_LENGTH..LARGE_NAV_BAR_LENGTH).random()
            if (count < LARGE_NAV_BAR_LENGTH) {
                removeItem(R.id.menu_item_e)
            }
            if (count < 4) {
                removeItem(R.id.menu_item_d)
            }
        }
    }

    private fun menuIndex(name: String) = when (name) {
        "A" -> R.id.menu_item_a
        "B" -> R.id.menu_item_b
        "C" -> R.id.menu_item_c
        "D" -> R.id.menu_item_d
        "E" -> R.id.menu_item_e
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

    fun navigate(name: String) {
        val backstack = stack.count { it.name == name }

        val info = FragmentInfo(name, backstack + 1)

        val prev = supportFragmentManager.findFragmentByTag("${stack.last()}")
            ?: throw IllegalArgumentException("Fragment not found ${stack.last()}")

        supportFragmentManager.beginTransaction()
            .add(
                R.id.screen_container,
                ComponentFragment::class.java,
                bundleOf(
                    "name" to info.name,
                    "backstack" to backstack + 1
                ),
                "$info"
            )
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

    companion object {
        const val SMALL_NAV_BAR_LENGTH = 3
        const val LARGE_NAV_BAR_LENGTH = 5
    }
}
