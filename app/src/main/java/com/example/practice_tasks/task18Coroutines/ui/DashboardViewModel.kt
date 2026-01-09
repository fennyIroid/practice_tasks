package com.example.practice_tasks.task18Coroutines.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice_tasks.task18Coroutines.data.DashboardRepository
import com.example.practice_tasks.task18Coroutines.models.AppNotification
import com.example.practice_tasks.task18Coroutines.models.Feedpost
import com.example.practice_tasks.task18Coroutines.models.Profile
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class DashboardViewModel(private val repo: DashboardRepository): ViewModel() {
    private val _profile = MutableStateFlow<Profile?>(null)
    val profile = _profile.asStateFlow()

    private val _feed = MutableStateFlow<List<Feedpost?>>(emptyList())
    val feed = _feed.asStateFlow()

    private val _notification = MutableStateFlow<List<AppNotification?>>(emptyList())
    val notification = _notification.asStateFlow()

    private val _feedError = MutableStateFlow(false)
    val feedError =_feedError.asStateFlow()

    fun loadDashboard() {
        viewModelScope.launch {
            supervisorScope {
                val profileDeferred = async { repo.fetchProfile() }
                val feedDeferred = async { repo.fetchFeed() }
                val notificationDeferred = async { repo.fetchNotifications() }

                try {
                    _profile.value = profileDeferred.await()
                }catch (_: Exception) {}

                try {
                    _feed.value = feedDeferred.await()
                }catch (_: Exception){
                    _feedError.value = true
                }

                    _notification.value = notificationDeferred.await()

            }
        }
    }
    fun cancelLoading() {
        viewModelScope.coroutineContext.cancelChildren()
    }
}