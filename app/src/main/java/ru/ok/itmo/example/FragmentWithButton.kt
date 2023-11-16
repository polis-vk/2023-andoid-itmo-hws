package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ru.ok.itmo.example.fragment_with_navigation.FragmentWithNavigation
import java.lang.IllegalArgumentException

class FragmentWithButton : Fragment(R.layout.fragment_with_button) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.count_fragments_value)

        parentFragmentManager.setFragmentResultListener(
            FragmentWithNavigation.Companion.ResultTags.RESULT,
            this
        )
        { _, bundle ->
            val result =
                bundle.getInt(FragmentWithNavigation.Companion.ResultTags.COUNT_FRAGMENTS, 0)
                    .takeIf { it != 0 } ?: throw IllegalArgumentException("Incorrect result")

            textView.text = result.toString()
        }

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
