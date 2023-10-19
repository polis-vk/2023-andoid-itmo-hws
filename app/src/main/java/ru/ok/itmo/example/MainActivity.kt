package ru.ok.itmo.example

import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.ok.itmo.example.MainViewModel.Mode.*
import ru.ok.itmo.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            progressBar.max = viewModel.selectedDuration
            startButton.setOnClickListener {
                setInProgressVisible(true)
                when (viewModel.mode) {
                    FLOW ->
                        lifecycleScope.launch {
                            viewModel.startFlow().onCompletion {
                                if (it == null) timeIsOutToast() else
                                    it.message?.let { message -> Log.d("Flow", message) }
                            }.onEach(::updateBar).collect()
                        }

                    THREAD -> viewModel.startOnThread(::updateBar, ::timeIsOutToast)
                }
            }
            modeGroup.setOnCheckedChangeListener { _, checkedId ->
                viewModel.mode = when (checkedId) {
                    R.id.flow_button -> FLOW
                    R.id.thread_button -> THREAD
                    else -> THREAD
                }
            }
            resetButton.setOnClickListener {
                viewModel.reset()
            }
            viewModel.durationList.forEach {
                durationGroup.addView(RadioButton(this@MainActivity).apply {
                    text = "${(it / 1000)} sec"
                    id = it
                    isChecked = it == viewModel.selectedDuration
                })
            }
            durationGroup.setOnCheckedChangeListener { _, checkedId ->
                viewModel.selectedDuration = checkedId
                progressBar.max = checkedId
                updateBar(0)
                viewModel.reset()
            }
            setInProgressVisible(false)
        }
        updateBar(0)
    }

    private fun updateBar(progress: Int) = runOnUiThread {
        binding.progressBar.setProgress(progress, false)
        binding.progressText.text =
            "${progress.toDouble() / 1000}/${viewModel.selectedDuration / 1000}"
    }

    private fun timeIsOutToast() = runOnUiThread {
        Toast.makeText(this, "Time is out on ${viewModel.mode} mode", Toast.LENGTH_LONG).show()
        setInProgressVisible(false)
    }

    fun setInProgressVisible(inProgress: Boolean) = with(binding) {
        startButton.isEnabled = inProgress xor true
        resetButton.isEnabled = inProgress xor false
        modeGroup.isEnabled = inProgress xor true
        flowButton.isEnabled = inProgress xor true
        threadButton.isEnabled = inProgress xor true
    }
}