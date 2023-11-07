package ru.ok.itmo.example

import android.view.Menu
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigationrail.NavigationRailView
import java.lang.IllegalArgumentException

interface NavigationViewInterface
{
    val menu: Menu
}

fun processNavigationView(view: FrameLayout): NavigationViewInterface {
    return when (view) {
        is BottomNavigationView -> BottomNavigationViewAdapter(view)
        is NavigationRailView -> NavigationRailViewAdapter(view)
        else -> throw IllegalArgumentException("This is not a legal navigationView")
    }
}

class BottomNavigationViewAdapter(private val parent: BottomNavigationView) : NavigationViewInterface {
    override val menu: Menu
        get() = parent.menu
}

class NavigationRailViewAdapter(private val parent: NavigationRailView) : NavigationViewInterface {
    override val menu: Menu
        get() = parent.menu
}
