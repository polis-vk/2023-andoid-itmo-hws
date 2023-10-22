package ru.ok.itmo.weatherapp.domain

import ru.ok.itmo.weatherapp.network.dto.WeatherResponce

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Data(val list: List<WeatherResponce>, val timeExecution: Long) : WeatherUiState()
    data class Error(val throwable: Throwable) : WeatherUiState()
}