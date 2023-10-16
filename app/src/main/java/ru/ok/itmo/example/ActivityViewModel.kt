package ru.ok.itmo.example

import androidx.lifecycle.ViewModel

class ActivityViewModel : ViewModel() {
    var started = false
    val options = arrayListOf(50L, 100, 300, 500)
    var currentOption = 1
    fun getCurrent(): Long {
        return options[currentOption]
    }

    fun changeByText(text: String) {
        for (i in options.indices) {
            if (options[i].toString() == text) {
                currentOption = i
                break
            }
        }
    }

}