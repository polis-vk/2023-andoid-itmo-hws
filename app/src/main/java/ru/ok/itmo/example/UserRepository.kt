package ru.ok.itmo.example

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class UserRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://faerytea.name:8008/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val userService: UserService = retrofit.create(UserService::class.java)

    suspend fun login(user: User): Result<LoginResponse> {
        try {
            val loginRequest = LoginRequest(user.username, user.password)
            val response = userService.login(loginRequest)
            return if (response.token != null) {
                Result.success(response)
            } else {
                Result.failure(Exception("Ошибка входа"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}

