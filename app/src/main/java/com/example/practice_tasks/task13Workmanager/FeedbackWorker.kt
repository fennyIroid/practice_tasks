package com.example.practice_tasks.task13Workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class FeedbackWorker(
    context: Context,
    params: WorkerParameters,
):Worker(context, params) {

    override fun doWork(): Result {
        val unsynced = FeedbackRepository.getSyncedFeedback()
         if (unsynced.isEmpty()) {
             Log.d("WORK", "No feedback to sync")
             return Result.success()
         }

        for (feedback in unsynced) {
            Log.d("WORK", "Syncing feedback: ${feedback.message}")
            Thread.sleep(1000)

            FeedbackRepository.markasSynced(feedback.id)
            Log.d("WORK", "Feedback synced: ${feedback.message}")
        }

        return Result.success()
        }
}
