package com.example.practice_tasks.task22Hilt.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.themeDataStore by preferencesDataStore(name = "theme_preferences")

object ThemeKeys {
    val DARK_MODE  = booleanPreferencesKey("dark_mode")
}


