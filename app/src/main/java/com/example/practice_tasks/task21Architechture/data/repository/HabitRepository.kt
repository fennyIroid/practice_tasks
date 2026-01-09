package com.example.practice_tasks.task21Architechture.data.repository

import com.example.practice_tasks.task21Architechture.data.model.Habit
import com.example.practice_tasks.task21Architechture.data.source.FakeHabitDataSource
import kotlinx.coroutines.flow.Flow

interface HabitRepository {

    fun getHabit() : Flow<List<Habit>>

    suspend fun markHabitDone(habitId: Int)
}