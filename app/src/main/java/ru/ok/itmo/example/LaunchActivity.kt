package ru.ok.itmo.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ok.itmo.example.databinding.ActivityLaunchBinding

class LaunchActivity : AppCompatActivity() {
    private var _binding: ActivityLaunchBinding? = null
    private val binding: ActivityLaunchBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.task1Btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.task2Btn.setOnClickListener {
            startActivity(Intent(this, RxMainActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}