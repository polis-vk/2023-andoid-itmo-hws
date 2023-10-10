package ru.ok.itmo.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class Fragment(val name: String) : Fragment(R.layout.fragment) {

    private lateinit var randomNumber: RandomNumber

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomNumber = ViewModelProvider(this)[RandomNumber::class.java]

        randomNumber.currentValue.observe(requireActivity()) {
            view.findViewById<TextView>(R.id.random_number).text = "$it"
        }

        view.findViewById<TextView>(R.id.fragment_name).text = name

//        view.findViewById<Button>(R.id.next).setOnClickListener { navigate(name) }
    }
}