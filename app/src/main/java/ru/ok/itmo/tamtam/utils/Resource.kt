package ru.ok.itmo.tamtam.utils

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure<T>(val throwable: Throwable) : Resource<T>()
}