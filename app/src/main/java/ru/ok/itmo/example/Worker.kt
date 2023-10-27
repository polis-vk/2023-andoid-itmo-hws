package ru.ok.itmo.example

interface Worker<T> {
    fun run(sleepTimeMs: Long): T
}