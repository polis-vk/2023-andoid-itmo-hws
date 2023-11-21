package ru.ok.itmo.example.login.repository

class FakeLoginRepository : LoginRepository {
    override suspend fun login(userCredentials: UserCredentials): Result<UserXAuthToken> {
        if (userCredentials.name.isNotEmpty() && userCredentials.password.isNotEmpty()) {
            return Result.success("kok")
        }
        return Result.failure(IllegalArgumentException())
    }
}