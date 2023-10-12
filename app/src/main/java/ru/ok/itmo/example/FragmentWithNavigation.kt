package ru.ok.itmo.example

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class FragmentWithNavigation : Fragment(R.layout.fragment_with_navigation) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val buttonContainer = view.findViewById<LinearLayout>(R.id.button_container)
//        val randomButtonCount = (3..5).random()
//
//        Log.d("dfs","dsf")
//
//        for (i in 1..randomButtonCount) {
//            Log.d("dfs","$i")
//            val button = Button(requireContext())
//            button.text = "Button $i"
//            buttonContainer.addView(button)
//
//            button.setOnClickListener {
//                Log.d("dfs","\"Button $i\"")
//            }
//        }
    }
}