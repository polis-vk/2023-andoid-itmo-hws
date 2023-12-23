package ru.ok.itmo.example.login.repository

import ru.ok.itmo.example.login.retrofit.models.UserCredentials
import java.lang.IllegalStateException

class UserDataRepository {
    companion object {
        private var userXAuthToken: UserXAuthToken = ""
        private var userCredentials: UserCredentials? = null
        private var currentChat: String = ""
    }

    fun login(userXAuthToken: UserXAuthToken, userCredentials: UserCredentials) {
        UserDataRepository.userXAuthToken = userXAuthToken
        UserDataRepository.userCredentials = userCredentials
    }

    fun logout() {
        currentChat = ""
        userXAuthToken = ""
        userCredentials = null
    }

    fun getToken(): UserXAuthToken {
        return userXAuthToken
    }

    fun getLogin(): String {
        return userCredentials?.name ?: throw RuntimeException("User not found")
    }

    fun openChat(chatName: String) {
        if (currentChat.isNotBlank()) {
            throw IllegalStateException("Can't open $chatName chat because chat $currentChat has already open")
        }
        currentChat = chatName
    }

    fun closeChat() {
        if (currentChat.isBlank()) {
            throw IllegalStateException("Can't found open chats")
        }
        currentChat = ""
    }

    fun getCurrentChat(): String {
        return currentChat
    }

    fun getPassword(): String {
        return userCredentials?.password ?: throw RuntimeException("User not found")
    }

    fun isLoggedIn(): Boolean {
        return userXAuthToken.isNotEmpty()
    }
}