package ru.ok.itmo.example.fragment_with_navigation

import android.view.Menu
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigationrail.NavigationRailView
import java.lang.IllegalArgumentException

interface NavigationViewInterface {
    val menu: Menu
    var selectedItemId: Int
    fun setOnItemSelectedListener(listener: NavigationBarView.OnItemSelectedListener)
}

fun processNavigationView(view: FrameLayout): NavigationViewInterface {
    return when (view) {
        is BottomNavigationView -> BottomNavigationViewAdapter(view)
        is NavigationRailView -> NavigationRailViewAdapter(view)
        else -> throw IllegalArgumentException("This is not a legal navigationView")
    }
}

class BottomNavigationViewAdapter(private val parent: BottomNavigationView) :
    NavigationViewInterface {
    override val menu: Menu
        get() = parent.menu

    override var selectedItemId: Int
        get() = parent.selectedItemId
        set(value) {
            parent.selectedItemId = value
        }

    override fun setOnItemSelectedListener(listener: NavigationBarView.OnItemSelectedListener) =
        parent.setOnItemSelectedListener(listener)
}

class NavigationRailViewAdapter(private val parent: NavigationRailView) : NavigationViewInterface {
    override val menu: Menu
        get() = parent.menu

    override var selectedItemId: Int
        get() = parent.selectedItemId
        set(value) {
            parent.selectedItemId = value
        }

    override fun setOnItemSelectedListener(listener: NavigationBarView.OnItemSelectedListener) =
        parent.setOnItemSelectedListener(listener)
}
