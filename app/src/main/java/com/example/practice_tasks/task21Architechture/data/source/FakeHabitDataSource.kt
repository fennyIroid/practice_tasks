package com.example.practice_tasks.task21Architechture.data.source

import com.example.practice_tasks.task21Architechture.data.model.Habit
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeHabitDataSource {
    private val habitsFlow = MutableStateFlow(
        listOf(
            Habit(1, "Drink Water", 7, false),
            Habit(2, "Exercise",3,false),
            Habit(3, "Read a Book",1,false),
        )
    )

    fun getHabits(): StateFlow<List<Habit>> = habitsFlow

    suspend fun markDone(habitId: Int) {
        delay(300)
        habitsFlow.value = habitsFlow.value.map { habit ->
            if (habit.id == habitId && !habit.isDoneToday){
                habit.copy(
                    streak = habit.streak + 1,
                    isDoneToday = true
                )
            } else {
                habit
            }

            }
        }
}
