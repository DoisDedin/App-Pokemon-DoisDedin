package com.example.pokemon_doisdedin.services.repository.local.datastore

import android.content.Context

import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.first

class DataStoreRepositoryLocal(context: Context) {
    private val dataStore = context.createDataStore(name = "data_store")

    companion object {
        val CACHE_TIME_KEY = preferencesKey<Long>("current_time")
    }

    suspend fun storeTime(currentTime: Long) {
        dataStore.edit {
            it[CACHE_TIME_KEY] = currentTime
        }
    }

    suspend fun readTime(): Long? {
        val preferences = dataStore.data.first()
        return preferences[CACHE_TIME_KEY]
    }
}
