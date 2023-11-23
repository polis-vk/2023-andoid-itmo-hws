package ru.ok.itmo.example.chatsPackage

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path


interface chatApi {
    @GET("/channels")
    fun getAllChannels(): Call<List<String>>

    @GET("/channel/{channelName}")
    fun getChannelMessages(@Path("channelName") name: String): Call<List<Message>>

    companion object {
        fun provideRequestApi(retrofit: Retrofit): chatApi {
            return retrofit.create(chatApi::class.java)
        }

    }

}