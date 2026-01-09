package com.example.practice_tasks.task18Coroutines.data

import com.example.practice_tasks.task18Coroutines.models.AppNotification
import com.example.practice_tasks.task18Coroutines.models.Feedpost
import com.example.practice_tasks.task18Coroutines.models.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import kotlin.random.Random

class DashboardRepository {
    suspend fun fetchProfile(): Profile = withContext(Dispatchers.IO) {
        delay(1500)
        Profile("Fenny", "Android Developer")
    }

    suspend fun fetchFeed(): List<Feedpost> = withContext(Dispatchers.IO) {
        delay(3000)
        if (Random.nextBoolean()) {
            throw Exception("Feed API failed")
        }
        listOf(
            Feedpost("Kotlin Coroutines"),
            Feedpost("Jetpack Compose"),
            Feedpost("Retrofit")
        )
    }

    suspend fun fetchNotifications(): List<AppNotification> = withContext(Dispatchers.IO) {
        delay(2000)
        listOf(
            AppNotification("New post"),
            AppNotification("New comment"),
            AppNotification("New follower")
        )
    }
}