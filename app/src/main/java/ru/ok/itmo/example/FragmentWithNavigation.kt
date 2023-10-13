package ru.ok.itmo.example

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigationrail.NavigationRailView

class FragmentWithNavigation(private var numberOfSections: Int = 0) : Fragment(R.layout.fragment_with_navigation) {
    private lateinit var navigationView: FrameLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null)
            numberOfSections = savedInstanceState.getInt("numberOfSections")

        navigationView = view.findViewById(R.id.nav_view)
        update(resources.configuration.orientation)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("numberOfSections", numberOfSections)
    }

    private fun update(orientation: Int)
    {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            for (i in 1..numberOfSections) {
                (navigationView as BottomNavigationView).menu.add(0, R.id.fragment_container, 0, "Sec: $i").setIcon(R.drawable.ic_launcher_foreground)
            }
        } else {
            for (i in 1..numberOfSections) {
                (navigationView as NavigationRailView).menu.add(0, R.id.fragment_container, 0, "Sec: $i").setIcon(R.drawable.ic_launcher_foreground)
            }
        }
    }
}