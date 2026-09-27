package com.example.rms.data.repository

import com.example.rms.domain.repository.LoginRepository
import kotlinx.coroutines.delay

class FakeLoginRepository(
    private val delayMs: Long = 1_000L,
) : LoginRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password must not be blank"))
        }
        delay(delayMs)
        return Result.success(Unit)
    }
}
