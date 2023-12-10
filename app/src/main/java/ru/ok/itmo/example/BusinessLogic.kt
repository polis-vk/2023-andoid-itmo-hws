package ru.ok.itmo.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BusinessLogic : ViewModel() {
    private fun generateRandomNumber() = (1..50).random()

    private val _randomNumber: MutableLiveData<Int> by lazy {
        MutableLiveData(generateRandomNumber())
    }
    val randomNumber: LiveData<Int> = _randomNumber
}