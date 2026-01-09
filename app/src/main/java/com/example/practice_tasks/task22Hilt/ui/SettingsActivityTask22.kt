package com.example.practice_tasks.task22Hilt.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.practice_tasks.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsActivityTask22 : AppCompatActivity() {

    val viewModel: SettingsViewModel by viewModels()

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings_task22)

        val themeSwitch = findViewById<Switch>(R.id.darkModeSwitch)

        lifecycleScope.launch {
            viewModel.isDarkTheme.collectLatest{ isDark ->
                themeSwitch.isChecked = isDark
                AppCompatDelegate.setDefaultNightMode(
                    if (isDark) {
                        AppCompatDelegate.MODE_NIGHT_YES
                    } else {
                        AppCompatDelegate.MODE_NIGHT_NO
                    }
                )
            }
        }
        themeSwitch.setOnCheckedChangeListener {
            _,isChecked ->
            viewModel.onThemeToggle(isChecked)
        }
    }
}