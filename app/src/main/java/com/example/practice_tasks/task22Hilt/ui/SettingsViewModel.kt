package com.example.practice_tasks.task22Hilt.ui

import androidx.lifecycle.viewModelScope
import com.example.practice_tasks.task19Stateflow.ui.ViewModel
import com.example.practice_tasks.task22Hilt.data.ThemePrefrenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePrefrenceManager: ThemePrefrenceManager): ViewModel(){
    val isDarkTheme = themePrefrenceManager.isDarkTheme

    fun onThemeToggle(enabled: Boolean) {
        viewModelScope.launch {
            themePrefrenceManager.setDarkTheme(enabled)
        }
    }
}