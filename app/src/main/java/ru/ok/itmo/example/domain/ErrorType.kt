package ru.ok.itmo.example.domain

sealed class ErrorType : Exception() {
    class Unknown : ErrorType()
    class Unauthorized : ErrorType()
    class InternetConnection : ErrorType()
}
