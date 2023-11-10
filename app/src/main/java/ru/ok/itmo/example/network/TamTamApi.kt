package ru.ok.itmo.example.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.ok.itmo.example.network.dto.LoginRequest

interface TamTamApi {
    @POST("/login")
    fun logIn(@Body loginRequest: LoginRequest): Call<String>
    @POST("/logout")
    fun logout(@Header("X-Auth-Token") token: String): Call<Unit>

    companion object {
        fun provideTamTamApi(retrofit: Retrofit): TamTamApi {
            return retrofit.create(TamTamApi::class.java)
        }
    }
}