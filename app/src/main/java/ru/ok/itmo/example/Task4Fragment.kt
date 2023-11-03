package ru.ok.itmo.example

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ok.itmo.example.databinding.FragmentTask4Binding

class Task4Fragment : Fragment() {
    private var _binding: FragmentTask4Binding? = null
    private val binding: FragmentTask4Binding
        get() = _binding ?: throw RuntimeException("FragmentTask4Binding is null")

    private var task4ViewModel: Task4ViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        task4ViewModel = ViewModelProvider(this)[Task4ViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTask4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeProgress()
    }

    private fun initListeners() {
        binding.launchBtn.setOnClickListener {
            task4ViewModel?.startProgress()
        }
    }

    private fun observeProgress() {
        lifecycleScope.launch {
            task4ViewModel?.progressFlow?.collect { value ->
                withContext(Dispatchers.Main) {
                    binding.progressBar.progress = value
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun getTask4FragmentInstance(): Task4Fragment {
            return Task4Fragment()
        }
    }
}