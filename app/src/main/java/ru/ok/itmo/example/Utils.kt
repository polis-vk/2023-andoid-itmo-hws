package ru.ok.itmo.example

fun String.isEmail(): Boolean {
    return this.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$".toRegex())
}

fun String.checkPassword(): Boolean = this.length > 5