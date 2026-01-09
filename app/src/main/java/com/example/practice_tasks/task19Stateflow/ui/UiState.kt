package com.example.practice_tasks.task19Stateflow.ui

import com.example.practice_tasks.task19Stateflow.model.User

sealed class UiState {
    object Idle: UiState()
    object Loading: UiState()

    data class Success(val users: List<User>): UiState()
    data class Error(val message: String): UiState()
}