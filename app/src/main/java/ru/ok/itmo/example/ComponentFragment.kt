package ru.ok.itmo.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider

class ComponentFragment : Fragment() {
    private lateinit var name: String
    private var backstack: Int = -1
    private var rnd: Int? = null
    private lateinit var randomProvider: RandomProviderViewModel
    private val frameTitle: String
        get() = "$name:$backstack"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        name = requireArguments().getString("name") ?: "Frame"
        backstack = requireArguments().getInt("backstack")
        rnd = savedInstanceState?.getInt("rnd")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.screen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomProvider = ViewModelProvider(this)[RandomProviderViewModel::class.java]

        view.findViewById<TextView>(R.id.fragment_name).text = frameTitle

        randomProvider.currentValue.observe(requireActivity()) {
            rnd = rnd ?: it
            view.findViewById<TextView>(R.id.random_number).text = "$rnd"
        }

        view.findViewById<Button>(R.id.next).setOnClickListener {
            (requireActivity().supportFragmentManager.findFragmentByTag(NAVIGATION) as NavigationFragment).navigate(name)
        }

        setFragmentResult(FRAGMENTS_COUNT, bundleOf(FRAGMENTS_COUNT to true))
    }

    override fun onDestroyView() {
        setFragmentResult(FRAGMENTS_COUNT, bundleOf(FRAGMENTS_COUNT to false))
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("rnd", rnd ?: 0)
    }
}
