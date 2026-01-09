package com.example.practice_tasks.task8intents

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practice_tasks.R

class ShareReceiverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_share_receiver)

        val textReceived = findViewById<TextView>(R.id.textReceived)

        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {

            val sharedText  = intent.getStringExtra(Intent.EXTRA_TEXT)
            textReceived.text = sharedText
        }
    }
}