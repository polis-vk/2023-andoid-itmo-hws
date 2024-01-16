package ru.ok.itmo.example

import com.google.gson.annotations.SerializedName;
import kotlinx.serialization.Serializable

@Serializable
class DataChat(@SerializedName("name") var name: String = "", @SerializedName("image") var image: String = "") {
}