package ru.ok.itmo.example.login.repository

import ru.ok.itmo.example.login.retrofit.LoginService

class RealLoginRepository : LoginRepository {
    override suspend fun login(userCredentials: UserCredentials): Result<UserXAuthToken> {
        return try {
            Result.success(LoginService.loginService.login(userCredentials).string())
        } catch(e: Throwable) {
            Result.failure(e)
        }
    }
}