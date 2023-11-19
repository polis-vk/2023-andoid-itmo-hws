package ru.ok.itmo.example.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import ru.ok.itmo.example.R
import ru.ok.itmo.example.databinding.FragmentHomePageScreenBinding

class HomePageScreen : Fragment() {

    lateinit var binding: FragmentHomePageScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homePageScreen_to_authScreen)
        }
    }
}