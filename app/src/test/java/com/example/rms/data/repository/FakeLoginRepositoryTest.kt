package com.example.rms.data.repository

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class FakeLoginRepositoryTest {
    @Test
    fun nonBlankCredentialsSucceed() = runTest {
        val result = FakeLoginRepository().login("user@example.com", "secret")

        assertTrue(result.isSuccess)
    }

    @Test
    fun blankCredentialsFailDefensively() = runTest {
        val result = FakeLoginRepository().login("", "secret")

        assertTrue(result.isFailure)
    }
}