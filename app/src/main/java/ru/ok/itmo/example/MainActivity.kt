package ru.ok.itmo.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {
//Kor; Danko; Far
//TheiPsUmWirEAmeT; HliehmEb; FerRakmWlitnY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, StartFragment())
            .commit()
    }
}