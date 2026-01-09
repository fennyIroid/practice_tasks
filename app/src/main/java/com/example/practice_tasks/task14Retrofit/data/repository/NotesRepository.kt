package com.example.practice_tasks.task14Retrofit.data.repository

import com.example.practice_tasks.task14Retrofit.data.model.AddNoteRequest
import com.example.practice_tasks.task14Retrofit.data.model.NotesModel
import com.example.practice_tasks.task14Retrofit.data.remote.RetrofitClient
import com.example.practice_tasks.task14Retrofit.data.util.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class NotesRepository {
    private val api = RetrofitClient.apiService

    suspend fun getNotes(): ApiResult<List<NotesModel>>{
        return withContext(Dispatchers.IO){
            try {
                val response = api.getNotes()

                if (response.isSuccessful) {
                    val data = response.body()

                    if (data != null){
                        ApiResult.Success(data)
                    }else{
                        ApiResult.Error("No Data Found")
                    }
                }else{
                    ApiResult.Error("Error: ${response.code()}")
                }
            }catch (e: Exception){
                ApiResult.Error("Error: ${e.message}")
            }
                }
            }

    suspend fun addNote(request: AddNoteRequest): ApiResult<NotesModel>{
        return withContext(Dispatchers.IO){
            try {
                val response = api.addNote(request)

                if (response.isSuccessful) {
                    val data = response.body()

                    if (data != null){
                        ApiResult.Success(data)
                    }else{
                        ApiResult.Error("Failed to add note")
                    }
                }else{
                    ApiResult.Error("Error: ${response.code()}")
                }
            }catch (e: Exception) {
                ApiResult.Error("Network Error: ${e.message}")
            }
        }
    }
}
