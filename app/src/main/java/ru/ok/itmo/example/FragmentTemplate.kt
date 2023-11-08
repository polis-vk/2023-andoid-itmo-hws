package ru.ok.itmo.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class FragmentTemplate : Fragment() {

    private lateinit var randomNumber: RandomNumber
    private var name: String = ""
    private val keyName: String = "name"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null)
            name = savedInstanceState.getString(keyName).toString()
        return inflater.inflate(R.layout.fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        randomNumber = ViewModelProvider(this)[RandomNumber::class.java]

        randomNumber.currentValue.observe(requireActivity()) {
            view.findViewById<TextView>(R.id.random_number).text = "$it"
        }

        view.findViewById<TextView>(R.id.fragment_name).text = name
    }

    companion object {
        fun newInstance(name: String): FragmentTemplate {
            val args = Bundle()
            args.putString("name", name)
            val f = FragmentTemplate()
            f.name = name
            return f
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(keyName, name)
    }
}