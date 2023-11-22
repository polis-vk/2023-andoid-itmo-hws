package ru.ok.itmo.example.login.retrofit

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import ru.ok.itmo.example.login.repository.UserCredentials

interface LoginAPI {
    @POST("/login")
    suspend fun login(@Body userCredentials: UserCredentials): ResponseBody
}