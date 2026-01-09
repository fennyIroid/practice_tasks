package com.example.practice_tasks.task21Architechture.data.repository

import com.example.practice_tasks.task21Architechture.data.model.Habit
import com.example.practice_tasks.task21Architechture.data.source.FakeHabitDataSource
import kotlinx.coroutines.flow.Flow

class HabitRepositoryImplementation(
    private val dataSource: FakeHabitDataSource
) : HabitRepository {

    override fun getHabit(): Flow<List<Habit>> {
        return dataSource.getHabits()
    }

    override suspend fun markHabitDone(habitId: Int) {
        dataSource.markDone(habitId)
    }
}
