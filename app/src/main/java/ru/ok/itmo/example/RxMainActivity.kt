package ru.ok.itmo.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.ok.itmo.example.databinding.ActivityRxMainBinding
import java.util.concurrent.TimeUnit

class RxMainActivity : AppCompatActivity() {
    private var _binding: ActivityRxMainBinding? = null
    private val binding: ActivityRxMainBinding get() = _binding!!

    @Volatile
    private var progress: Int = 100
    private var timeStep: Long = 100L

    private val disposables = CompositeDisposable()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PROGRESS_VALUE, progress)
        outState.putLong(TIME_STEP_VALUE, timeStep)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRxMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        restoreArgs(savedInstanceState)
        initListeners()

        if (progress < 100) {
            startProgress()
        }
    }

    private fun restoreArgs(savedInstanceState: Bundle?) {
        savedInstanceState?.getInt(PROGRESS_VALUE)?.let {
            progress = it
        }
        savedInstanceState?.getLong(TIME_STEP_VALUE)?.let {
            timeStep = it
        }
    }

    private fun initListeners() {
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            timeStep = when (checkedId) {
                binding.radioButton50.id -> 50L
                binding.radioButton100.id -> 100L
                binding.radioButton300.id -> 300L
                binding.radioButton500.id -> 500L
                else -> throw RuntimeException("Unknown id")
            }
        }
        binding.restart.setOnClickListener {
            restartProgress()
        }
    }

    private fun startProgress() {
        disposables.add(
            Observable.interval(timeStep, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .takeWhile { progress < 100 }
                .subscribe {
                    progress++
                    binding.progressBar.progress = progress
                }
        )
    }

    private fun stopProgress() {
        disposables.clear()
    }

    private fun restartProgress() {
        stopProgress()
        progress = 0
        startProgress()
    }


    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        _binding = null
    }

    companion object {
        private const val TIME_STEP_VALUE = "time_step"
        private const val PROGRESS_VALUE = "progress"
    }
}