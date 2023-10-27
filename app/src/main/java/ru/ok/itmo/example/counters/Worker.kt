package ru.ok.itmo.example.counters

interface Worker<T> {
    fun run(sleepTimeMs: Long): T
}