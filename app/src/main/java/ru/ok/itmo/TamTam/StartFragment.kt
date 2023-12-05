package ru.ok.itmo.TamTam

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class StartFragment : Fragment(R.layout.start_fragment) {
    override fun onStart() {
        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.accent)

        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val controller = findNavController()

        view.findViewById<TextView>(R.id.enterByLogin).setOnClickListener {
            controller.navigate(R.id.loginFragment)
        }

        view.findViewById<TextView>(R.id.enterByPhone).setOnClickListener {
            Toast.makeText(requireContext(), R.string.not_implemented, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        super.onStop()
    }
}
