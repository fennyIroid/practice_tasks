package com.example.practice_tasks.task24ComposeCore

import android.R.attr.name
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practice_tasks.task24ComposeCore.data.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HabitViewModel: ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchHabitsFromApi()
    }

    private fun fetchHabitsFromApi() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val habitNames = RetrofitClient.api.getHabits()
                println("Fetched habits: $habitNames")


                _habits.value = habitNames.map { name ->
                    Habit(title = name)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun addHabitApi(name: String) {
        viewModelScope.launch {
            try {
                val updated = RetrofitClient.api.addHabit(
                    mapOf("name" to name)
                )
                _habits.value = updated.map {
                    Habit(it) }
            }catch (e: Exception){
                e.printStackTrace()
            }
            println("Added habit: $name")
        }
    }

    fun deleteHabitApi(habit: Habit) {
        viewModelScope.launch {
            val updated = RetrofitClient.api.deleteHabit(habit.title)
            _habits.value = updated.map { Habit(it) }
        }
    }


    fun toggleHabit(habit: Habit) {
        _habits.update { list ->
            list.map {
                if (it == habit) it.copy(isDone = !it.isDone)
                else it
            }
        }
    }

}
