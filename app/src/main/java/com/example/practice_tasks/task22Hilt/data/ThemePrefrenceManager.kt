package com.example.practice_tasks.task22Hilt.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemePrefrenceManager @Inject constructor(@ApplicationContext private val context: Context) {
    val isDarkTheme: Flow<Boolean> =
        context.themeDataStore.data.map {prefs ->
            prefs[ThemeKeys.DARK_MODE] ?: false
        }

    suspend fun setDarkTheme(enabled: Boolean){
        context.themeDataStore.edit { prefs ->
            prefs[ThemeKeys.DARK_MODE] = enabled
        }
    }


}