package ru.ok.itmo.example.login.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.ok.itmo.example.login.retrofit.LoginAPI
import ru.ok.itmo.example.login.retrofit.models.UserCredentials
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val apiService: LoginAPI
) {
    fun login(userCredentials: UserCredentials): Flow<UserXAuthToken> {
        return flowOf(apiService.login(userCredentials))
    }
}