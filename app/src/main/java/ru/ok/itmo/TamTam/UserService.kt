package ru.ok.itmo.TamTam

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {
    @POST("/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body loginRequest: LoginRequest): ResponseBody

    @POST("/get")
    suspend fun login2(): ResponseBody
}