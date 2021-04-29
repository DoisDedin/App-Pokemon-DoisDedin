package com.example.pokemon_doisdedin.services.constants

import androidx.datastore.preferences.preferencesKey

class Constants private constructor() {

    object HTTP {
        const val SUCCESS = 200
    }
    object VALUES{
        const val SIZE = 30
    }
    object LINK{
        const val POKEMOMIMAGE = "https://pokeres.bastionbot.org/images/pokemon/"
    }
    object DATA_STORE{
        const val PREFERENCE_NAME = "my_preference"
        object PREFERENCES_KEYS{
            val NAME = preferencesKey<String>("my_name")
        }
    }
}