package ru.ok.itmo.example.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.ok.itmo.example.MainActivity
import ru.ok.itmo.example.R

class HomeFragment : Fragment(R.layout.home_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar?.title = resources.getString(R.string.home_header)
    }
}