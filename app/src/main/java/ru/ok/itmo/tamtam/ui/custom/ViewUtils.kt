package ru.ok.itmo.tamtam.ui.custom

import android.graphics.Color
import kotlin.random.Random

class ViewUtils {
    companion object {
        fun getRandomColor(): Int = Color.rgb(
            Random.nextInt(0, 255),
            Random.nextInt(0, 255),
            Random.nextInt(0, 255)
        )
    }
}
