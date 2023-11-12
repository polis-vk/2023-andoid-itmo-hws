package ru.ok.itmo.tamtam.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response
import ru.ok.itmo.tamtam.data.retrofit.AuthService
import ru.ok.itmo.tamtam.data.retrofit.model.UserRequest
import ru.ok.itmo.tamtam.ioc.scope.AppComponentScope
import ru.ok.itmo.tamtam.presentation.core.NotificationType
import ru.ok.itmo.tamtam.utils.Resource
import javax.inject.Inject

@AppComponentScope
class AuthRepository @Inject constructor(
    private val accountStorage: AccountStorage,
    private val authService: AuthService
) {
    private val _isAuth = MutableStateFlow(accountStorage.token != null)
    val isAuth: StateFlow<Boolean> get() = _isAuth

    suspend fun login(login: String, password: String): Resource<Unit> {
        val resource = runCatching {
            authService.login(
                userRequest = UserRequest(
                    name = login,
                    pwd = password
                )
            )
        }.handleResult()
        return when (resource) {
            is Resource.Success -> {
                accountStorage.token = resource.data
                _isAuth.emit(true)
                Resource.Success(Unit)
            }

            is Resource.Failure -> Resource.Failure(resource.throwable)
        }
    }

    suspend fun logout(): Resource<Unit> {
        runCatching {
            authService.logout()
        }
        accountStorage.clear()
        _isAuth.emit(false)
        return Resource.Success(Unit)
    }

    private fun <T> Result<Response<T>>.handleResult(): Resource<T> {
        val exception = this.exceptionOrNull()
        if (exception != null) return Resource.Failure(NotificationType.Connection(exception))
        val response = this.getOrNull()
            ?: return Resource.Failure(NotificationType.Unknown(RuntimeException("No response")))

        return when (response.code()) {
            200 -> {
                val body = response.body()
                    ?: return Resource.Failure(NotificationType.Unknown(RuntimeException("No body")))
                Resource.Success(body)
            }

            400, in 402..499 -> Resource.Failure(NotificationType.Client(RuntimeException("Client exception")))
            401 -> Resource.Failure(NotificationType.Unauthorized(RuntimeException("Unauthorized")))
            in 500..599 -> Resource.Failure(NotificationType.Server(RuntimeException("Server exception")))
            else -> Resource.Failure(NotificationType.Unknown(RuntimeException("Unknown exception")))
        }
    }


}