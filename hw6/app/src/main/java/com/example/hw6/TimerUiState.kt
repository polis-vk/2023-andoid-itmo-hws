package com.example.hw6

sealed class TimerUiState {
    data class Data(val timeExecution: Int) : TimerUiState()
}