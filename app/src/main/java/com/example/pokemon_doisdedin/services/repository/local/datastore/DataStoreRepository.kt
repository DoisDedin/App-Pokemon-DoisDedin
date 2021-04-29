package com.example.pokemon_doisdedin.services.repository.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import com.example.pokemon_doisdedin.services.constants.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class DataStoreRepository(context: Context) {
    private val dataStore: DataStore<androidx.datastore.preferences.Preferences> =
        context.createDataStore(
            name = Constants.DATA_STORE.PREFERENCE_NAME
        )

    suspend fun saveDataStore(name: String) {
        dataStore.edit { preference ->
            preference[Constants.DATA_STORE.PREFERENCES_KEYS.NAME] = name
        }
    }

    val readDataStore: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException){
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            }else {
                throw exception
            }
        }
        .map {  preference ->
            val myName = preference[Constants.DATA_STORE.PREFERENCES_KEYS.NAME] ?: "NONE"
            myName
        }
}
