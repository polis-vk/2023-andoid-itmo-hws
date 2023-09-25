package ru.ok.itmo.hw

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.ok.itmo.hw.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        val TAG = MainActivity::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val launcher = registerForActivityResult(ResultContract()) {
            Log.d(TAG, it.name)
        }
        launcher.launch(DataInfo("Hello world!"))
    }
}