package com.example.rms.domain.repository

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<Unit>
}
