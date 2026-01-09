package com.example.practice_tasks.task13Workmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.practice_tasks.R

class Task13MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task13_main)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val etFeedback = findViewById<EditText>(R.id.etFeedback)

        btnSubmit.setOnClickListener {
            val message = etFeedback.text.toString()

            if (message.isEmpty()) return@setOnClickListener

            FeedbackRepository.addFeedback(message)
            startSyncWork()

            Toast.makeText(this,"Feedback saved offline, Will sync Automatically", Toast.LENGTH_LONG).show()

            etFeedback.text.clear()

        }
    }
    fun startSyncWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<FeedbackWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }
}