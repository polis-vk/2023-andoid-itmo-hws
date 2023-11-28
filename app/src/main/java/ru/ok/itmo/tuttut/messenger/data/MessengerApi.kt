package ru.ok.itmo.tuttut.messenger.data

import okhttp3.ResponseBody
import retrofit2.http.Header
import retrofit2.http.POST
import ru.ok.itmo.tuttut.login.domain.UserXAuthToken

interface MessengerApi {
    @POST("/messages")
    suspend fun messages(@Header("X-Auth-Token") authToken: UserXAuthToken): ResponseBody
}