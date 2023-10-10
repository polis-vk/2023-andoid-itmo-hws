package ru.ok.itmo.example

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class RandomNumber : ViewModel() {
    val currentValue: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().apply {
            value = Random.nextInt(100)
        }
    }
}
