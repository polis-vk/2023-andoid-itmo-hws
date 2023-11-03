package ru.ok.itmo.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ok.itmo.example.databinding.FragmentTask2Binding

class Task2Fragment : Fragment() {
    private var _binding: FragmentTask2Binding? = null
    private val binding: FragmentTask2Binding
        get() = _binding ?: throw RuntimeException("FragmentTask2Binding is null")

    @Volatile
    private var progress: Int = PROGRESS_MAX_VALUE
    private var progressJob: Job? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PROGRESS_VALUE, progress)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreArgs(savedInstanceState)
    }

    private fun restoreArgs(savedInstanceState: Bundle?) {
        savedInstanceState?.getInt(PROGRESS_VALUE)?.let {
            progress = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTask2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (progress < PROGRESS_MAX_VALUE) loadProgress()
        binding.launchBtn.setOnClickListener {
            progress = PROGRESS_MIN_VALUE
            loadProgress()
        }
    }

    private fun loadProgress() {
        progressJob?.cancel()
        progressJob = lifecycleScope.launch(Dispatchers.IO) {
            createProgressFlow().collect { value ->
                withContext(Dispatchers.Main) {
                    binding.progressBar.progress = value
                }
            }
        }
    }

    private fun createProgressFlow(): Flow<Int> = flow {
        while (true) {
            delay(100)
            progress++
            emit(progress)
        }
    }.flowOn(Dispatchers.IO)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PROGRESS_MAX_VALUE = 100
        private const val PROGRESS_MIN_VALUE = 0
        private const val PROGRESS_VALUE = "progress"

        fun getTask2FragmentInstance(): Task2Fragment {
            return Task2Fragment()
        }
    }
}