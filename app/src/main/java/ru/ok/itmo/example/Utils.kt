package ru.ok.itmo.example

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

class Utils {
    companion object {
        fun isNightModeActive(context: Context): Boolean {
            val defaultNightMode = AppCompatDelegate.getDefaultNightMode()
            if (defaultNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                return true
            }
            if (defaultNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
                return false
            }

            return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> false
                Configuration.UI_MODE_NIGHT_YES -> true
                Configuration.UI_MODE_NIGHT_UNDEFINED -> false
                else -> false
            }
        }
    }
}
