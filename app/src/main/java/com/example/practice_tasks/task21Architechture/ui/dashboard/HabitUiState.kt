package com.example.practice_tasks.task21Architechture.ui.dashboard

import com.example.practice_tasks.task21Architechture.data.model.Habit

data class HabitUiState(
    val habits: List<Habit> = emptyList(),
    val isLoading: Boolean = true
)
