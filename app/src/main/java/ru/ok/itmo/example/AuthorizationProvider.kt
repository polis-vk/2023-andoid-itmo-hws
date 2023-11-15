package ru.ok.itmo.example

sealed class ErrorType : Exception() {
    class Unknown : ErrorType()
    class Unauthorized : ErrorType()
    class InternetConnection : ErrorType()
}

data object AuthorizationProvider {
    private val client = LoginApi.provideLoginApi(RetrofitProvider.retrofit)

    suspend fun login(userAuthorization: UserAuthorization): Result<String?> {
        return try {
            val response = client.login(userAuthorization)
            return when (response.code()) {
                200 -> Result.success(response.body())
                401 -> Result.failure(ErrorType.Unauthorized())
                else -> Result.failure(ErrorType.Unknown())
            }
        } catch (e: Exception) {
            Result.failure(ErrorType.InternetConnection())
        }
    }
}
