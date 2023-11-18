package ru.ok.itmo.example

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StartedFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_started, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val act = activity as AppCompatActivity
        act.supportActionBar!!.hide()

        view.findViewById<Button>(R.id.buttonLogin).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_startedFragment_to_loginFragment)
        }
    }
}