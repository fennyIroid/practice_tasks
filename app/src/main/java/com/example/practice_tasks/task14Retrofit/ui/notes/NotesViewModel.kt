package com.example.practice_tasks.task14Retrofit.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice_tasks.task14Retrofit.data.model.AddNoteRequest
import com.example.practice_tasks.task14Retrofit.data.model.NotesModel
import com.example.practice_tasks.task14Retrofit.data.repository.NotesRepository
import com.example.practice_tasks.task14Retrofit.data.util.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel: ViewModel() {
    private val repository = NotesRepository()
    private val _notesState = MutableStateFlow<ApiResult<List<NotesModel>>>(ApiResult.Loading)

    val notesState: StateFlow<ApiResult<List<NotesModel>>> = _notesState

    private val _addNoteState = MutableStateFlow<ApiResult<NotesModel>>(ApiResult.Idle)

    val addNoteState: StateFlow<ApiResult<NotesModel>> = _addNoteState

    fun fetchNotes() {
        viewModelScope.launch {
            _notesState.value = ApiResult.Loading
            _notesState.value = repository.getNotes()
        }
    }

    fun addNote(title: String, body: String) {
        val request = AddNoteRequest(
            title = title,
            body = body,
            userId = 1
        )

        viewModelScope.launch {
            _addNoteState.value = ApiResult.Loading
            _addNoteState.value = repository.addNote(request)
        }
    }
}