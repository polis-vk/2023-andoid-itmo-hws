package ru.ok.itmo.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.security.SecureRandom

class RandomViewModel : ViewModel() {
    private val random = SecureRandom(byteArrayOf(1, 2, 12))
    val number: LiveData<Int> by lazy {
        MutableLiveData<Int>().apply {
            value = random.nextInt(1000)
        }
    }
}