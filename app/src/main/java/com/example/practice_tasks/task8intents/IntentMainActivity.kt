package com.example.practice_tasks.task8intents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practice_tasks.R

class IntentMainActivity : AppCompatActivity() {

    private val pickFile = registerForActivityResult(ActivityResultContracts.GetContent()) {
        uri -> if (uri != null) {
            println("Picked file URI: $uri")
    }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intent_main)

        val btnWebsite = findViewById<Button>(R.id.btnWebsite)
        val btnMaps = findViewById<Button>(R.id.btnMaps)
        val btnPickFile = findViewById<Button>(R.id.btnPickFile)
        val btnShareText = findViewById<Button>(R.id.btnShareText)


        btnWebsite.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://developer.android.com/guide/components/intents-filters"))
            startActivity(intent)
        }

        btnMaps.setOnClickListener {
            val location = Uri.parse("geo:0,0?q=5QRP+87 Surat, Gujarat")
            val intent = Intent(Intent.ACTION_VIEW, location)
            startActivity(intent)
        }

        btnPickFile.setOnClickListener {
            pickFile.launch("*/*")
        }

        btnShareText.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Hello from IntentPlayground!")
            }
            startActivity(Intent.createChooser(intent, "Share via"))
        }

    }
}