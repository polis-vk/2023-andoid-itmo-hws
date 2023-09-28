package ru.ok.itmo.example

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.result.contract.ActivityResultContract

class LoginResultContract : ActivityResultContract<Unit?, LoginData>() {
    companion object {
        const val RESULT_KEY = "result_key"
    }

    override fun createIntent(context: Context, input: Unit?): Intent = Intent(
        context,
        LoginActivity::class.java,
    )

    override fun parseResult(resultCode: Int, intent: Intent?): LoginData {
        val loginData: LoginData? = if (resultCode == Activity.RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra(RESULT_KEY, LoginData::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent?.getParcelableExtra(RESULT_KEY)
            }
        } else null
        return loginData ?: throw IllegalStateException("Failed with an error")
    }
}
