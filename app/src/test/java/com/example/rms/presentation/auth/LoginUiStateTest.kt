package com.example.rms.presentation.auth

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginUiStateTest {
    @Test
    fun initialStateHasExpectedDefaults() {
        val state = LoginUiState()

        assertTrue(state.email.isEmpty())
        assertTrue(state.password.isEmpty())
        assertNull(state.emailError)
        assertNull(state.passwordError)
        assertFalse(state.isLoading)
        assertFalse(state.loginSuccess)
    }
}