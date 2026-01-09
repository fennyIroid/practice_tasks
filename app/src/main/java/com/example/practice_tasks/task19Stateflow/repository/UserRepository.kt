package com.example.practice_tasks.task19Stateflow.repository

import com.example.practice_tasks.task19Stateflow.model.User
import com.example.practice_tasks.task19Stateflow.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class UserRepository {
    fun getUsers(): Flow<List<User>> = flow {
        val response = RetrofitClient.api.getUsers()
        emit(response)
    }

        .map { userDtos ->
            userDtos.map {dto ->
                User(
                    id = dto.id,
                    name = dto.name
                )
            }
        }
        .onEach {
            println("Fetched ${it.size} users")
        }
        .catch {
            emit(emptyList())
        }
        .flowOn(Dispatchers.IO)
}