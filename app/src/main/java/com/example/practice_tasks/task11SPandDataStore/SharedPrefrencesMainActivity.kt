package com.example.practice_tasks.task11SPandDataStore

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practice_tasks.R

class SharedPrefrencesMainActivity : AppCompatActivity() {

    private lateinit var SharedPref : SharedPreferences
    private lateinit var resultTxtView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shared_prefrences_main)

        SharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        resultTxtView = findViewById<TextView>(R.id.txtViewResult)

        val etName = findViewById<EditText>(R.id.edtTxtName)
        val etAge = findViewById<EditText>(R.id.edtTxtAge)
        val isDark = findViewById<Switch>(R.id.switchDark)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnRead = findViewById<Button>(R.id.btnRead)
        val btnCounter = findViewById<Button>(R.id.btnCounter)

        btnSave.setOnClickListener {
            val editor = SharedPref.edit()
            editor.putString("name", etName.text.toString())
            editor.putInt("age",etAge.text.toString().toInt())
            editor.putBoolean("dark",isDark.isChecked)

            val count = SharedPref.getInt("counter",0)
            editor.putInt("counter",count+1)

            editor.apply()
        }

        btnRead.setOnClickListener {
            val name = SharedPref.getString("name", "")
            val age = SharedPref.getInt("age",0)
            val dark = SharedPref.getBoolean("dark",false)
            val counter = SharedPref.getInt("counter", 0)

            resultTxtView.text = "Name: $name\nAge: $age\nDark: $dark\nCounter: $counter"

        }
    }
}