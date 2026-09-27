package com.example.rms.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class SessionManager private constructor(
    private val dataStore: DataStore<Preferences>,
    @Suppress("UNUSED_PARAMETER") marker: Unit,
) {
    constructor(context: Context) : this(context.sessionDataStore, Unit)

    internal constructor(dataStore: DataStore<Preferences>) : this(dataStore, Unit)

    private val isAuthenticatedKey = booleanPreferencesKey("is_authenticated")

    val isAuthenticated: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[isAuthenticatedKey] ?: false }

    suspend fun setAuthenticated(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[isAuthenticatedKey] = value
        }
    }

    suspend fun clearSession() {
        setAuthenticated(false)
    }
}