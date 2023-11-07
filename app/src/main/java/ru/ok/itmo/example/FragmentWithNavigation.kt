package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class FragmentWithNavigation(private var numberOfSections: Int = 0) : Fragment(R.layout.fragment_with_navigation) {
    private lateinit var navigationView: NavigationViewInterface

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null)
            numberOfSections = savedInstanceState.getInt("numberOfSections")

        navigationView = processNavigationView(view.findViewById(R.id.nav_view))
        update()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("numberOfSections", numberOfSections)
    }

    private fun update()
    {
        for (i in 1..numberOfSections) {
            navigationView.menu.add(0, R.id.fragment_container, 0, "Sec: $i").setIcon(R.drawable.ic_launcher_foreground)
        }
    }
}