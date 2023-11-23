package ru.ok.itmo.tamtam

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import ru.ok.itmo.tamtam.model.Model
import ru.ok.itmo.tamtam.start.SplashFragmentDirections
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SharedViewModel : ViewModel(), KoinComponent {
    companion object {
        const val DEFAULT_TOKEN = "default_token"
    }

    val modelInstance: Model by inject()

    lateinit var navController: NavController
    var token: String = DEFAULT_TOKEN

    fun logout() {
        token = DEFAULT_TOKEN
        closeAll()
    }

    fun login(newToken: String) {
        token = newToken
    }

    fun startApp() {
        if (token == DEFAULT_TOKEN) {
            navController.navigate(SplashFragmentDirections.actionSplashFragmentToLoginNavGraph())
        } else {
            navController.navigate(SplashFragmentDirections.actionSplashFragmentToAppNavGraph())
        }
    }

    private fun closeAll() {
        navController.popBackStack(R.id.start_nav_graph, true)
        navController.navigate(R.id.start_nav_graph)
        startApp()
    }
}