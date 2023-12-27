package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.home_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun showNav() {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                when (val nav = requireActivity().supportFragmentManager.findFragmentByTag(NAVIGATION)) {
                    null -> add(R.id.main_container, NavigationFragment(), NAVIGATION)
                    else -> show(nav)
                }
            }.hide(this)
                .addToBackStack(NAVIGATION)
                .commit()
        }

        view.findViewById<Button>(R.id.go).setOnClickListener {
            showNav()
        }

        if (savedInstanceState?.getBoolean(SAVED_BUNDLE_ACTIVE_NAVIGATION) == true) {
            showNav()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(SAVED_BUNDLE_ACTIVE_NAVIGATION,
            requireActivity().supportFragmentManager.findFragmentByTag(NAVIGATION) != null)
    }

    private companion object {
        const val SAVED_BUNDLE_ACTIVE_NAVIGATION = "active-navigation"
    }
}
