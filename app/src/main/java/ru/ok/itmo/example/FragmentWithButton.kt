package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.ok.itmo.example.fragment_with_navigation.FragmentWithNavigation
import java.lang.IllegalArgumentException

class FragmentWithButton : Fragment(R.layout.fragment_with_button) {

    companion object {
        class FragmentWithButtonViewModel(private val savedStateHandle: SavedStateHandle) :
            ViewModel() {
            private var _countFragments: Int?
                get() = savedStateHandle[FragmentWithNavigation.Companion.ResultTags.COUNT_FRAGMENTS]
                set(value) {
                    savedStateHandle[FragmentWithNavigation.Companion.ResultTags.COUNT_FRAGMENTS] =
                        value
                }

            var countFragments: MutableLiveData<Int?> = MutableLiveData(_countFragments)
                set(value) {
                    _countFragments = value.value
                    field = value
                }
        }
    }

    private val viewModel: FragmentWithButtonViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.count_fragments_value)

        viewModel.countFragments.observe(viewLifecycleOwner) { value ->
            textView.text = value?.toString() ?: getString(R.string.fragment_counter_preview)
        }

        parentFragmentManager.setFragmentResultListener(
            FragmentWithNavigation.Companion.ResultTags.RESULT,
            this
        )
        { _, bundle ->
            val result =
                bundle.getInt(
                    FragmentWithNavigation.Companion.ResultTags.COUNT_FRAGMENTS,
                    Int.MIN_VALUE
                )
                    .takeIf { it != Int.MIN_VALUE }
                    ?: throw IllegalArgumentException("Incorrect result")

            viewModel.countFragments.value = result
        }

        btnStartLogic(view)
    }

    private fun btnStartLogic(view: View) {
        val button = view.findViewById<Button>(R.id.btn_start)
        button.setOnClickListener {
            val fragment = FragmentWithNavigation.newInstance((3..5).random())
            parentFragmentManager.commit {
                setPrimaryNavigationFragment(fragment)
                replace(
                    R.id.fragment_main_container,
                    fragment
                )
                addToBackStack(null)
            }
        }
    }
}
