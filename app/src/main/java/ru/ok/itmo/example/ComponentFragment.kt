package ru.ok.itmo.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class ComponentFragment(
    private val name: String,
    private val backstack: Int,
    private val navigate: (name: String) -> Unit
) : Fragment() {
    private lateinit var randomNumber: BackstackViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.screen_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomNumber = ViewModelProvider(this)[BackstackViewModel::class.java]

        view.findViewById<TextView>(R.id.fragment_name).text = "$name:$backstack"

        randomNumber.currentValue.observe(requireActivity(), Observer {
            view.findViewById<TextView>(R.id.random_number).text = "$it"
        })

        view.findViewById<Button>(R.id.next).setOnClickListener { navigate(name) }
    }
}
