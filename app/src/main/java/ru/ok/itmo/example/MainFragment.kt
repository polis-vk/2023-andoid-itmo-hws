package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<RandomViewModel>()

    private lateinit var button: Button
    private lateinit var numberTextView: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button = view.findViewById(R.id.button)
        numberTextView = view.findViewById(R.id.number_tv)
        button.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_container, NavigationFragment::class.java, Bundle())
                addToBackStack(null)
            }
        }
        viewModel.number.observe(viewLifecycleOwner) {
            numberTextView.text = resources.getString(R.string.random_number, it)
        }
    }
}