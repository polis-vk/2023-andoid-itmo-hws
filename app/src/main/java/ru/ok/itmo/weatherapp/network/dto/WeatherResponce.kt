package ru.ok.itmo.weatherapp.network.dto

import com.google.gson.annotations.SerializedName

class WeatherResponce {

    @SerializedName("location")
    var location: Location = Location()

    @SerializedName("current")
    var current: Current = Current()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherResponce

        if (location != other.location) return false
        if (current != other.current) return false

        return true
    }

    override fun hashCode(): Int {
        var result = location.hashCode()
        result = 31 * result + current.hashCode()
        return result
    }


}