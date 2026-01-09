package com.example.practice_tasks.task19Stateflow.ui

import androidx.lifecycle.viewModelScope
import com.example.practice_tasks.task19Stateflow.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel


open class ViewModel(private val repository: UserRepository = UserRepository()): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent: SharedFlow<String> = _toastEvent

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            repository.getUsers().collect {users ->
                if (users.isEmpty()) {
                    _uiState.value = UiState.Error("No users found")
                } else {
                    _uiState.value = UiState.Success(users)
                    _toastEvent.emit("Users loaded successfully!")
                }
            }
        }
    }
}