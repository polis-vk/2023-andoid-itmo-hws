package ru.ok.itmo.example

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: ViewModel(){
    val progress = MutableLiveData(0)

    fun fill(){
        progress.value = 0
        viewModelScope.launch(Dispatchers.IO){
            while(progress.value != 100) {
                delay(100)
                viewModelScope.launch(Dispatchers.Main) {
                    progress.value = progress.value?.plus(1)
                }
            }
        }
    }
}