package ru.ok.itmo.example

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity(R.layout.activity_main) {

    companion object {
        val TAG: String = this::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tvFormLogin = findViewById<TextView>(R.id.tv_form_login)

        val launcher = registerForActivityResult(LoginResultContract()) {
            tvFormLogin.text = it.id
            Log.i(TAG, "LoginResultContract $it")
        }

        try {
            launcher.launch(null)
        } catch (e : IllegalStateException) {
            tvFormLogin.text = e.toString()
            Log.e(TAG, e.toString())
        }
    }
}
