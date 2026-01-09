package com.example.practice_tasks.task17FirebaseCrashlytics

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practice_tasks.R
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics

class Task17MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task17_main)

        val analytics = Firebase.analytics

        val btnForceCrash = findViewById<Button>(R.id.btnForceCrash)
        val btnCustomException = findViewById<Button>(R.id.btnCustomException)

        analytics.logEvent("crash_analytics_screen_opened", null)
        btnForceCrash.setOnClickListener {
            throw RuntimeException("This is a forced crash")
        }

        btnCustomException.setOnClickListener {
            analytics.logEvent("custom_crash_button_clicked", null)
            FirebaseCrashlytics.getInstance().log("Custom crash log button clicked")
            FirebaseCrashlytics.getInstance().recordException(Exception("Manually logged crash"))
        }
    }
}