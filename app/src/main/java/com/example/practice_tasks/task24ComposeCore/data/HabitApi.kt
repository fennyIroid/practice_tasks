package com.example.practice_tasks.task24ComposeCore.data

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HabitApi {
    @GET("habits")
    suspend fun getHabits(): List<String>

    @POST("habits")
    suspend fun addHabit(
        @Body body: Map<String, String>
    ): List<String>

    @DELETE("habits/{name}")
    suspend fun deleteHabit(
        @Path("name") name: String
    ): List<String>
}