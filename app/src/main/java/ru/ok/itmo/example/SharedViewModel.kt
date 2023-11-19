package ru.ok.itmo.example

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import ru.ok.itmo.example.login.FragmentLogin

class SharedViewModel : ViewModel() {
    companion object {
        const val DEFAULT_TOKEN = "default_token"
    }

    lateinit var fragmentManager: FragmentManager
    private var token: String = DEFAULT_TOKEN

    fun logout() {
        token = DEFAULT_TOKEN
        closeAll()
        startApp()
    }

    fun login(newToken: String) {
        token = newToken
        closeAll()
        startApp()
    }

    fun startApp() {
        fragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_main_container,
                if (token == DEFAULT_TOKEN) FragmentLogin() else FragmentBack()
            )
        }
    }

    private fun closeAll() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}