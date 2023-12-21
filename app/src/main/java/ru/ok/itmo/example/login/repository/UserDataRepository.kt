package ru.ok.itmo.example.login.repository

import ru.ok.itmo.example.login.retrofit.models.UserCredentials

class UserDataRepository {
    companion object {
        private var userXAuthToken: UserXAuthToken = ""
        private var userCredentials: UserCredentials? = null
    }

    fun login(userXAuthToken: UserXAuthToken, userCredentials: UserCredentials) {
        UserDataRepository.userXAuthToken = userXAuthToken
        UserDataRepository.userCredentials = userCredentials
    }
    
    fun logout() {
        userXAuthToken = ""
        userCredentials = null
    }
    
    fun getToken(): UserXAuthToken {
        return userXAuthToken
    }
    
    fun getLogin(): String {
        return userCredentials?.name ?: throw RuntimeException("User not found")
    }

    fun getPassword(): String {
        return userCredentials?.password ?: throw RuntimeException("User not found")
    }
    
    fun isLoggedIn(): Boolean {
        return userXAuthToken.isNotEmpty()
    }
}