package com.example.pokemon_doisdedin.services.repository.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import com.example.pokemon_doisdedin.services.constants.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "my_preference"

class DataStoreRepository(context: Context) {
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
//    val timeFlow: Flow<Long> =dataStore.data.map {
//        it[CACHE_TIME_KEY] ?: -1
//    }
}
