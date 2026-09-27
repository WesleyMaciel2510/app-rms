package com.example.rms.data.local

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import java.io.File
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SessionManagerTest {
    private fun testFile(): File = File.createTempFile("session-test-", ".preferences_pb").also {
        it.deleteOnExit()
        it.delete()
    }

    @Test
    fun initialValueDefaultsToFalse() = runTest {
        val store = PreferenceDataStoreFactory.create(scope = backgroundScope) { testFile() }
        assertFalse(SessionManager(store).isAuthenticated.first())
    }

    @Test
    fun setAuthenticatedTrueEmitsTrue() = runTest {
        val store = PreferenceDataStoreFactory.create(scope = backgroundScope) { testFile() }
        val manager = SessionManager(store)

        manager.setAuthenticated(true)

        assertTrue(manager.isAuthenticated.first())
    }

    @Test
    fun clearSessionEmitsFalse() = runTest {
        val store = PreferenceDataStoreFactory.create(scope = backgroundScope) { testFile() }
        val manager = SessionManager(store)
        manager.setAuthenticated(true)

        manager.clearSession()

        assertFalse(manager.isAuthenticated.first())
    }
}