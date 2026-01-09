package com.example.practice_tasks.task11SPandDataStore

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.example.practice_tasks.R
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatDelegate



class DataStoreActivity : AppCompatActivity() {

    private lateinit var txtResult: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data_store)

        val etName = findViewById<EditText>(R.id.edtTxtName)
        val etAge = findViewById<EditText>(R.id.edtTxtAge)
        val switchDark = findViewById<Switch>(R.id.switchDark)
        txtResult = findViewById<TextView>(R.id.txtViewResult)
        val btnSave = findViewById<Button>(R.id.btnSave)


        //never put collect inside any component's event it should start collecting when the screen opens
        lifecycleScope.launch {
            dataStore.data.collect {prefs ->
                val name = prefs[NAME] ?: ""
                val age = prefs[AGE] ?: 0
                val dark = prefs[DARK] ?: false
                val counter = prefs[COUNTER] ?: 0

                switchDark.isChecked = dark

                if (dark) {
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )
                }else {
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )
                }

                txtResult.text = "Name: $name\nAge: $age\nDark Mode: $dark\nCounter: $counter"
            }
        }

        switchDark.setOnCheckedChangeListener{_, isChecked ->
            lifecycleScope.launch {
                dataStore.edit { prefs ->
                    prefs[DARK] = isChecked
                }
            }

            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
            }else {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }

        btnSave.setOnClickListener {
            lifecycleScope.launch {
                dataStore.edit { prefs ->
                    prefs[NAME] = etName.text.toString()
                    prefs[AGE] = etAge.text.toString().toInt()
                    prefs[DARK] = switchDark.isChecked

                }
            }

        }

        findViewById<Button>(R.id.btnCounter).setOnClickListener {
            lifecycleScope.launch {
                dataStore.edit { prefs ->
                    val count = prefs[COUNTER] ?: 0
                    prefs[COUNTER] = count + 1
                }
            }
        }


    }
}