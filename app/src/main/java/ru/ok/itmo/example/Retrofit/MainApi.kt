package ru.ok.itmo.example.Retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MainApi {
    @POST("/login")
    fun logIn(@Body loginRequest: LoginRequest): Call<String>
    @POST("/logout")
    suspend fun logout(@Header("X-Auth-Token") token: String?): Response<String>
}