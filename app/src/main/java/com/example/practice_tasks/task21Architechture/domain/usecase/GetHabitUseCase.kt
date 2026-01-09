package com.example.practice_tasks.task21Architechture.domain.usecase

import com.example.practice_tasks.task21Architechture.data.model.Habit
import com.example.practice_tasks.task21Architechture.data.repository.HabitRepository
import kotlinx.coroutines.flow.Flow

class GetHabitUseCase(private val repository : HabitRepository) {

    operator fun invoke(): Flow<List<Habit>> {
        return repository.getHabit()
    }

}