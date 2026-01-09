package com.example.practice_tasks.task14Retrofit.data.remote

import com.example.practice_tasks.task14Retrofit.data.model.AddNoteRequest
import com.example.practice_tasks.task14Retrofit.data.model.NotesModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
@GET("posts")
suspend fun getNotes(): Response<List<NotesModel>>

@POST("posts")
suspend fun addNote(@Body note: AddNoteRequest) : Response<NotesModel>
}