package com.example.practice_tasks.task16PushNotifications

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.practice_tasks.R
import com.google.firebase.messaging.FirebaseMessaging

class ReminderDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remainder_details)

        val reminderId = intent.getStringExtra("reminderId") ?: intent.getStringExtra("REMINDER_ID") ?: "Unknown"

        findViewById<TextView>(R.id.txtDetail).text =
            """
            📘 Study Reminder

            Reminder ID: $reminderId

            DSA Revision scheduled at 6:00 PM
            """.trimIndent()
    }
}
