package ru.ok.itmo.weatherapp.domain

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function3
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import ru.ok.itmo.weatherapp.network.WeatherApi
import ru.ok.itmo.weatherapp.network.dto.WeatherResponce

const val City1 = "Moscow"
const val City2 = "London"
const val City3 = "Tokyo"

class WeatherUseCase (private val weatherApi: WeatherApi) {

    fun rxZipVersion(): Single<List<WeatherResponce>> {
        return Single.zip(
            weatherApi.getWeatherByCityRx(City1, "yes"),
            weatherApi.getWeatherByCityRx(City2),
            weatherApi.getWeatherByCityRx(City3),
            Function3<WeatherResponce, WeatherResponce, WeatherResponce, List<WeatherResponce>> { moscow, london, tokyo ->
                mutableListOf(moscow, london, tokyo)
            }
        )
    }

    fun rxFlatmapVersion(): Single<List<WeatherResponce>> {
        val list: MutableList<WeatherResponce> = mutableListOf()
        return weatherApi.getWeatherByCityRx(City1)
            .flatMap { moscow ->
                list.add(moscow)
                weatherApi.getWeatherByCityRx(City2)
            }
            .flatMap { london ->
                list.add(london)
                weatherApi.getWeatherByCityRx(City3)
            }
            .map { tokio ->
                list.add(tokio)
                list
            }
    }

    suspend fun coroutineVersion(): List<WeatherResponce> {
        val moscow = weatherApi.getWeatherByCityCor(City1)
        val london = weatherApi.getWeatherByCityCor(City2)
        val tokyo = weatherApi.getWeatherByCityCor(City3)
        return listOf(moscow, london, tokyo)
    }

    suspend fun coroutineAsyncVersion(): List<WeatherResponce> {
        return withContext(Dispatchers.IO) {
            val deferredMoscow = async(start = CoroutineStart.LAZY) {
                weatherApi.getWeatherByCityCor(City1)
            }
            val deferredLondon = async(start = CoroutineStart.LAZY) {
                weatherApi.getWeatherByCityCor(City2)
            }
            val deferredTokyo = async(start = CoroutineStart.LAZY) {
                weatherApi.getWeatherByCityCor(City3)
            }
            val mos = deferredMoscow.await()
            val lon = deferredLondon.await()
            val tok = deferredTokyo.await()
            return@withContext listOf(mos, lon, tok)
        }
    }

    fun flowFlatMapVersion(): Flow<List<WeatherResponce>> {
        val list: MutableList<WeatherResponce> = mutableListOf()
        return weatherApi.getWeatherByCityFlow(City1)
            .flatMapMerge { res ->
                list.add(res)
                weatherApi.getWeatherByCityFlow(City2)
            }
            .flatMapMerge { res ->
                list.add(res)
                weatherApi.getWeatherByCityFlow(City3)
            }
            .map { res ->
                list.add(res)
                list
            }
    }

    fun flowZipVersion(): Flow<List<WeatherResponce>> {
        val city1Flow = weatherApi.getWeatherByCityFlow(City1)
        val city2Flow = weatherApi.getWeatherByCityFlow(City2)
        val city3Flow = weatherApi.getWeatherByCityFlow(City3)
        val zippedCity1AndCity2 = city1Flow
            .zip(city2Flow) { city1, city2 -> mutableListOf(city1, city2) }

        return zippedCity1AndCity2.zip(city3Flow) { city1AndCity2, city3 ->
            city1AndCity2.add(city3)
            city1AndCity2
        }
    }

}