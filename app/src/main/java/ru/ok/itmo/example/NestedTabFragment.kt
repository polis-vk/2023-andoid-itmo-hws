package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class NestedTabFragment : Fragment(R.layout.fragment_tab_nested) {
    private val viewModel by viewModels<RandomViewModel>()

    private lateinit var randomIntTv: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        randomIntTv = view.findViewById(R.id.random_int_tv)
        viewModel.number.observe(viewLifecycleOwner) {
            randomIntTv.text = resources.getString(R.string.random_number, it)
        }
    }
}