package ru.ok.itmo.example

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginData(val id: String) : Parcelable
