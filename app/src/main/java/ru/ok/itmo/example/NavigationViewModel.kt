package ru.ok.itmo.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.security.SecureRandom

class NavigationViewModel : ViewModel() {
    private val random: SecureRandom = SecureRandom(byteArrayOf(2, 3, 23))

    companion object {
        val NAMES = arrayOf("A", "B", "C", "D", "E")
    }

    val tabs: LiveData<ArrayList<String>> by lazy {
        MutableLiveData<ArrayList<String>>().apply {
            val randomN = random.nextInt(3) + 3
            value = ArrayList(NAMES.slice(0 until randomN))
        }
    }
    var navInit = true
    var currentTab = -1
}