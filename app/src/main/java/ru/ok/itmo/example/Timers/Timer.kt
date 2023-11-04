package ru.ok.itmo.example.Timers

interface TimerProtocol {
    var sleepTime: Long

    fun run(completion: (Int) -> Unit)
    fun reset()
}
