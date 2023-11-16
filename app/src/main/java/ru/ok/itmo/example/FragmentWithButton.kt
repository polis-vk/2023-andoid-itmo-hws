package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ru.ok.itmo.example.fragment_with_navigation.FragmentWithNavigation

class FragmentWithButton : Fragment(R.layout.fragment_with_button) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnStartLogic(view)
    }

    private fun btnStartLogic(view: View) {
        val button = view.findViewById<Button>(R.id.btn_start)
        button.setOnClickListener {
            val fragment = FragmentWithNavigation.newInstance((3..5).random())
            parentFragmentManager.commit {
                setPrimaryNavigationFragment(fragment)
                replace(
                    R.id.fragment_main_container,
                    fragment
                )
                addToBackStack(null)
            }
        }
    }
}
