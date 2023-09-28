package ru.ok.itmo.example

data class ValidationResult(val hasError: Boolean, val errorMessage: String?) {
    companion object {
        val OK = ValidationResult(false, null)
    }
}