package com.example.pokemon_doisdedin.services.repository.local.datastore

import kotlinx.coroutines.flow.Flow

interface UserSettingsStore {
    suspend fun readTime() : Flow<Long>
    suspend fun storeTime(time : Long)
}