package com.example.practice_tasks.task21Architechture.ui.dashboard

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.example.practice_tasks.task21Architechture.domain.usecase.GetHabitUseCase
import com.example.practice_tasks.task21Architechture.domain.usecase.MarkHabitDone
import com.example.practice_tasks.task21Architechture.ui.dashboard.HabitUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch



class HabitViewModel
    (private val getHabitUseCase: GetHabitUseCase,
    private val markHabitDoneUseCase: MarkHabitDone
): ViewModel() {

     val uiState: StateFlow<HabitUiState> =
        getHabitUseCase()
            .map { habits ->
                HabitUiState(
                    habits = habits,
                    isLoading = false
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HabitUiState()
            )

    fun onHabitDoneClicked(habitId: Int) {
        viewModelScope.launch {
            markHabitDoneUseCase(habitId)
        }
    }
}