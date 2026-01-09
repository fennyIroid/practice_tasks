package com.example.practice_tasks.task19Stateflow.remote

import com.example.practice_tasks.task19Stateflow.model.UserDto
import retrofit2.http.GET

interface UserApi {

    @GET("users")
    suspend fun getUsers(): List<UserDto>
}