package ru.ok.itmo.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.w3c.dom.Text

class SimpleActionBar : Fragment(R.layout.action_bar_simple) {

    var title: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.buttonBack)
            .setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

        view.findViewById<TextView>(R.id.title)
            .text = title
    }
}