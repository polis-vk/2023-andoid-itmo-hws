package ru.ok.itmo.example.timers

interface TimerProtocol {
    var sleepTime: Long

    fun run(completion: (Int) -> Unit)
    fun reset()
}
