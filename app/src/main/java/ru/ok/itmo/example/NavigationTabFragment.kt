package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels

class NavigationTabFragment : Fragment(R.layout.fragment_tab_navigation) {
    private val viewModel by viewModels<RandomViewModel>()

    private lateinit var button: Button
    private lateinit var randIntTv: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button = view.findViewById(R.id.button)
        randIntTv = view.findViewById(R.id.random_int_tv)
        setOrDefault(
            button,
            arguments?.getString(NavigationFragment.FRAGMENT_NAME_KEY),
            resources.getString(R.string.none)
        )
        viewModel.number.observe(viewLifecycleOwner) {
            randIntTv.text = resources.getString(R.string.random_number, it)
        }

        button.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.navigation_fragment_container, NestedTabFragment::class.java, bundleOf())
                addToBackStack(button.text.toString())
            }
        }
    }

    private fun setOrDefault(view: Button, text: Any?, default: Any) {
        view.text = text?.toString() ?: default.toString()
    }
}