package com.example.practice_tasks.task11SPandDataStore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "user_dataStore")

val NAME = stringPreferencesKey("name")
val AGE = intPreferencesKey("age")
val DARK = booleanPreferencesKey("dark")
val COUNTER = intPreferencesKey("counter")

class DataStoreManager {

}