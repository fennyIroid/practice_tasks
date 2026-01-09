package com.example.practice_tasks.task21Architechture.domain.usecase

import com.example.practice_tasks.task21Architechture.data.repository.HabitRepository

class MarkHabitDone(private val repository: HabitRepository) {

    suspend operator fun invoke(habitId: Int) {
        repository.markHabitDone(habitId)
    }
}