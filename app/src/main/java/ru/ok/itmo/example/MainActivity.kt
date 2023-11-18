package ru.ok.itmo.example

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)!!
//        val navController = navHostFragment.findNavController()
//        navController.navigate(R.id.loginFragment)
    }

}