package com.example.pokemon_doisdedin.services.repository.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserSettingsStoreImp (private val context: Context) : UserSettingsStore {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

    companion object {
        val CACHE_TIME_KEY = longPreferencesKey("current_time")
    }

    override suspend fun readTime(): Flow<Long> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[CACHE_TIME_KEY] ?: 0
        }

    override suspend fun storeTime(time: Long) {
        context.dataStore.edit { dataStore ->
            dataStore[CACHE_TIME_KEY] = time
        }
    }
}