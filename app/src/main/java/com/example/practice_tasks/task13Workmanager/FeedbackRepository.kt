package com.example.practice_tasks.task13Workmanager

object FeedbackRepository {
    private val feedbackList = mutableListOf<Feedback>()

    fun addFeedback(message: String) {
        feedbackList.add(
            Feedback(
                id = feedbackList.size + 1,
                message = message,
                isSynced = false
            )
        )
    }

    fun getSyncedFeedback(): List<Feedback> {
        return feedbackList.filter { !it.isSynced }
    }

    fun markasSynced(id:Int) {
        feedbackList.find { it.id == id }?.isSynced = true
    }
}