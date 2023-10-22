package ru.ok.itmo.weatherapp.network

import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import ru.ok.itmo.weatherapp.network.dto.WeatherResponce

interface WeatherApi {

    @GET("/v1/current.json")
    fun getWeatherByCityRx(@Query("q") city: String, @Query("aqi")  aqi: String = "no"): Single<WeatherResponce>

    @GET("/v1/current.json")
    suspend fun getWeatherByCityCor(@Query("q") city: String, @Query("aqi")  aqi: String = "no"): WeatherResponce

    @GET("/v1/current.json")
    fun getWeatherByCityFlow(@Query("q") city: String, @Query("aqi")  aqi: String = "no"): Flow<WeatherResponce>

    companion object {
        fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
            return retrofit.create(WeatherApi::class.java)
        }
    }
}