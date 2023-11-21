package ru.ok.itmo.example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StartedFragment : Fragment(R.layout.fragment_started) {
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.buttonLogin).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_startedFragment_to_loginFragment)
        }
    }
}