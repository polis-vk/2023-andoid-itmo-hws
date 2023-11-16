package ru.ok.itmo.example

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BusinessLogic : ViewModel() {
    val randomNumber: MutableLiveData<Int> by lazy {
        MutableLiveData((1..50).random())
    }
}