package ru.ok.itmo.tamtam

import android.content.Context
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

class Helper {
    companion object {
        fun setButtonColor(button: Button, context: Context, color: Int) {
            val tintedBackground = DrawableCompat.wrap(button.background).mutate()
            DrawableCompat.setTint(tintedBackground, getColor(context, color))
            button.background = tintedBackground
        }

        fun getColor(context: Context, color: Int): Int {
            return ContextCompat.getColor(
                context, color
            )
        }
    }
}