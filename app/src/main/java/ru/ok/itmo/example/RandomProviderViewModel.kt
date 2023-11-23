package ru.ok.itmo.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class RandomProviderViewModel : ViewModel() {
    private val randomProvider: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().apply {
            value = Random.nextInt(100)
        }
    }

    val currentValue: LiveData<Int> = randomProvider
}
