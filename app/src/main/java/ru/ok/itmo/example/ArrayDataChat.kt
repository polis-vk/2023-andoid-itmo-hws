package ru.ok.itmo.example

import kotlinx.serialization.Serializable

@Serializable
data class ArrayDataChat(var data: Array<DataChat>? = arrayOf<DataChat>())