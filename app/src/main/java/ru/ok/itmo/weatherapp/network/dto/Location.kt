package ru.ok.itmo.weatherapp.network.dto

import com.google.gson.annotations.SerializedName

class Location {
    @SerializedName("name")
    var city: String = ""

    @SerializedName("country")
    var country: String = ""
}