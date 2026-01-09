package com.example.practice_tasks.task11SPandDataStore

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practice_tasks.R

class Task11MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task11_main)

        val btnSharedPrefrences = findViewById<Button>(R.id.btnSharedPrefrences)
        val btnDataStore = findViewById<Button>(R.id.btnDataStore)


        btnSharedPrefrences.setOnClickListener {
            val intent = Intent(this, SharedPrefrencesMainActivity::class.java)
            startActivity(intent)
        }

        btnDataStore.setOnClickListener {
            val intent = Intent(this, DataStoreActivity::class.java)
            startActivity(intent)
        }

    }
}