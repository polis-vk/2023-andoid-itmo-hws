package ru.ok.itmo.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val mutableSelectedItem = MutableLiveData<Int>()
    val selectedItem: LiveData<Int> get() = mutableSelectedItem

    fun selectItem(item: Int) {
        mutableSelectedItem.value = item
    }

}