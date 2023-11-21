package ru.ok.itmo.example.login.repository

interface LoginRepository {
    suspend fun login(userCredentials: UserCredentials): Result<UserXAuthToken>
}

typealias UserXAuthToken = String
