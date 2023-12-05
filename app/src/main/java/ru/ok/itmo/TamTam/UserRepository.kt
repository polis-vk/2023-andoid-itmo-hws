package ru.ok.itmo.TamTam

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object UserRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://faerytea.name:8008/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val userService: UserService = retrofit.create(UserService::class.java)

    suspend fun login(user: User): Result<String> {
        try {
            val loginRequest = LoginRequest(user.username, user.password)

            Log.d("LOGin_REP", userService.toString())

            val response = userService.login(loginRequest)

            Log.d("LOGin_REP+res", loginRequest.toString())
            return if (response.toString().isNotEmpty()) {
                Result.success(response.toString())
            } else {
                Result.failure(Exception("Ошибка входа"))
            }

        } catch (e: Exception) {
            return Result.failure(e)
        }

    }
}

