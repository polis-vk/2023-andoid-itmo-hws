package ru.ok.itmo.example.counters

class Counter {
    var value = 0
        private set

    companion object {
        const val MIN = 0
        const val MAX = 100
    }

    fun updateCounter(): Boolean {
        ++value
        return value <= MAX
    }

    fun initCounter() {
        value = MIN
    }
}