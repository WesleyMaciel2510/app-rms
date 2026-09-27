package com.example.rms.presentation.auth

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.example.rms.data.local.SessionManager
import com.example.rms.data.repository.FakeLoginRepository
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginViewModelTest {
    private fun testFile(): File = File.createTempFile("login-test-", ".preferences_pb").also {
        it.deleteOnExit()
        it.delete()
    }

    private fun sessionManager(scope: kotlinx.coroutines.CoroutineScope): SessionManager =
        SessionManager(PreferenceDataStoreFactory.create(scope = scope) { testFile() })

    @Test
    fun initialStateHasNoErrorsAndIsNotLoading() {
        val viewModel = LoginViewModel(FakeLoginRepository(0), sessionManager(kotlinx.coroutines.CoroutineScope(Dispatchers.Unconfined)))

        assertFalse(viewModel.uiState.value.isLoading)
        assertTrue(viewModel.uiState.value.emailError == null)
        assertTrue(viewModel.uiState.value.passwordError == null)
    }

    @Test
    fun emailChangeUpdatesState() {
        val viewModel = LoginViewModel(FakeLoginRepository(0), sessionManager(kotlinx.coroutines.CoroutineScope(Dispatchers.Unconfined)))

        viewModel.onEmailChange("user@example.com")

        assertTrue(viewModel.uiState.value.email == "user@example.com")
    }

    @Test
    fun blankEmailSetsErrorWithoutLoading() {
        val viewModel = LoginViewModel(FakeLoginRepository(0), sessionManager(kotlinx.coroutines.CoroutineScope(Dispatchers.Unconfined)))

        viewModel.onLoginClick()

        assertNotNull(viewModel.uiState.value.emailError)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun validCredentialsSetLoadingThenSuccess() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = LoginViewModel(FakeLoginRepository(), sessionManager(kotlinx.coroutines.CoroutineScope(Dispatchers.Unconfined)))
            viewModel.onEmailChange("user@example.com")
            viewModel.onPasswordChange("secret")

            viewModel.onLoginClick()
            runCurrent()
            assertTrue(viewModel.uiState.value.isLoading)

            advanceUntilIdle()
            assertTrue(viewModel.uiState.value.loginSuccess)
            assertFalse(viewModel.uiState.value.isLoading)
        } finally {
            Dispatchers.resetMain()
        }
    }
}