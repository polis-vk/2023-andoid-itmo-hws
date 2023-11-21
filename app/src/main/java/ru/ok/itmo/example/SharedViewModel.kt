package ru.ok.itmo.example

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class SharedViewModel : ViewModel() {
    companion object {
        const val DEFAULT_TOKEN = "default_token"
    }

    lateinit var navController: NavController
    private var token: String = DEFAULT_TOKEN

    fun logout() {
        token = DEFAULT_TOKEN
        closeAll()
    }

    fun login(newToken: String) {
        token = newToken
    }

    fun startApp() {
        if (token == DEFAULT_TOKEN) {
            navController.navigate(R.id.action_splashFragment_to_login_nav_graph)
        } else {
            navController.navigate(R.id.action_splashFragment_to_app_nav_graph)
        }
    }

    private fun closeAll() {
        navController.popBackStack(navController.graph.startDestinationId, false)
        startApp()
    }
}