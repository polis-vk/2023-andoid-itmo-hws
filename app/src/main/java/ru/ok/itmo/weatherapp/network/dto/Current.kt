package ru.ok.itmo.weatherapp.network.dto

import com.google.gson.annotations.SerializedName

class Current {
    @SerializedName("temp_c")
    var tempC: Int = 0
}