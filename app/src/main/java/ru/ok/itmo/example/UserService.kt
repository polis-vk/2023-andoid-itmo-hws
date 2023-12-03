package ru.ok.itmo.example

import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}