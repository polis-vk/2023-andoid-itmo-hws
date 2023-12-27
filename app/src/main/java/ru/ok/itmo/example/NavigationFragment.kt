package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationBarView

const val FRAGMENTS_COUNT = "fragments-count"

class NavigationFragment : Fragment(R.layout.activity_navigation) {
    private class FragmentInfo(val name: String, val backstack: Int) {
        override fun toString() = "$name:$backstack"
    }

    private var stack = mutableListOf<FragmentInfo>()
    private lateinit var navigation: NavigationBarView
    private lateinit var randomProvider: RandomProviderViewModel

    private var fragmentsCount = 0
    private var toast: Toast? = null

    private val supportFragmentManager: FragmentManager
        get() = requireActivity().supportFragmentManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        randomProvider = ViewModelProvider(this)[RandomProviderViewModel::class.java]

        val savedStack = savedInstanceState?.getStringArray(SAVED_BUNDLE_STACK)?.map {
            val (name, backstack) = it.split(":")
            FragmentInfo(name, backstack.toInt())
        }

        if (savedStack.isNullOrEmpty()) {
            randomProvider.currentValue.observe(requireActivity()) { rnd ->
                stack += FragmentInfo("A", 1)

                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.screen_container,
                        ComponentFragment::class.java,
                        bundleOf(
                            "name" to "A",
                            "backstack" to 1
                        ),
                        "${stack.last()}"
                    )
                    .commit()
            }
        } else {
            stack += savedStack
            savedStack.map { it to supportFragmentManager.findFragmentByTag("$it")!! }.run {
                supportFragmentManager.beginTransaction()
                    .show(first().second)
                    .commit()

                zipWithNext().forEach { (prev, cur) ->
                    supportFragmentManager.beginTransaction()
                        .show(cur.second)
                        .hide(prev.second)
                        .addToBackStack("${cur.first}")
                        .commit()
                }
            }
        }

        navigation = view.findViewById(R.id.navigation_bar)

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
            val count = savedInstanceState?.getInt(SAVED_BUNDLE_MENU_SIZE)
                ?: (SMALL_NAV_BAR_LENGTH..LARGE_NAV_BAR_LENGTH).random()
            if (count < LARGE_NAV_BAR_LENGTH) {
                removeItem(R.id.menu_item_e)
            }
            if (count < 4) {
                removeItem(R.id.menu_item_d)
            }
        }

        setFragmentResultListener(FRAGMENTS_COUNT) { _, bundle ->
            fragmentsCount += if (bundle.getBoolean(FRAGMENTS_COUNT)) 1 else -1
            toast?.cancel()
            toast = Toast.makeText(view.context, "Total: $fragmentsCount", Toast.LENGTH_SHORT)
            toast!!.show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putStringArray(SAVED_BUNDLE_STACK, stack.map { it.toString() }.toTypedArray())
        outState.putInt(SAVED_BUNDLE_MENU_SIZE, navigation.menu.size())
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

    fun popBackStack(): Boolean {
        if (stack.size == 1) {
            return false
        }

        val last = supportFragmentManager.findFragmentByTag("${stack.last()}")!!
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().remove(last).commit()
        stack.removeLast()
        navigation.selectedItemId = menuIndex(stack.last().name)

        return true
    }

    private companion object {
        const val SMALL_NAV_BAR_LENGTH = 3
        const val LARGE_NAV_BAR_LENGTH = 5

        const val SAVED_BUNDLE_STACK = "stack"
        const val SAVED_BUNDLE_MENU_SIZE = "menu-size"
    }
}
