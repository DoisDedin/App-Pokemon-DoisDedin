package com.example.pokemon_doisdedin.services.auxiliares


import com.example.pokemon_doisdedin.services.repository.local.datastore.UserSettingsStore
import com.example.pokemon_doisdedin.services.repository.local.datastore.UserSettingsStoreImp

private const val TIME_CACHE_VALID = 86400000L // 60 * 60 * 24 * 1000

class ValidationTime(private var dataStore: UserSettingsStore) {

    suspend fun cacheIsValid(currentTime: Long): Int {
        val oldTime = dataStore.readTime()
        if (oldTime == null) {
            return 0 //not local database
        } else {
            val result = (currentTime - oldTime)
            if (result > TIME_CACHE_VALID) {
                return 1 // local database and is invalid
            } else {
                return 2 // local database and is valid
            }
        }
    }
}

//create by Guilherme ( Morcego )
//private const val ABOUT_TEXT_KEY = "ABOUT_TEXT_KEY"
//private const val ABOUT_TEXT_CACHE_TIME = "ABOUT_TEXT_CACHE_TIME"
//private const val TIME_CACHE_VALID = 86400000L // 60 * 60 * 24 * 1000
//class AboutLocalDataSourceImpl(
//    private val sharedPreferences: SharedPreferences
//) : AboutLocalDataSource {
//    override suspend fun saveAboutText(aboutText: String) {
//        withContext(IO) {
//            sharedPreferences.edit()
//                .putString(ABOUT_TEXT_KEY, aboutText)
//                .putLong(ABOUT_TEXT_CACHE_TIME, System.currentTimeMillis())
//                .apply()
//        }
//    }
//    override suspend fun getAboutText(): String =
//        withContext(IO) { sharedPreferences.getString(ABOUT_TEXT_KEY, Constants.EMPTY_STRING) ?: Constants.EMPTY_STRING }
//    override suspend fun isValidCache(): Boolean {
//        val isAboutTexSaved = withContext(IO) { sharedPreferences.contains(ABOUT_TEXT_KEY) }
//        val lastCacheTime = withContext(IO) { sharedPreferences.getLong(ABOUT_TEXT_CACHE_TIME, 0) }
//        val now = System.currentTimeMillis()
//        return isCacheSavedMoreThanOneDay(now, lastCacheTime).not() && isAboutTexSaved
//    }
//    private fun isCacheSavedMoreThanOneDay(now: Long, lastCacheTime: Long) = now - lastCacheTime >= TIME_CACHE_VALID
//}