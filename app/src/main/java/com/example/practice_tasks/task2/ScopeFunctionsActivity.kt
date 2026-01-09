package com.example.practice_tasks.task2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.practice_tasks.databinding.ActivityScopeFunctionsBinding

class ScopeFunctionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScopeFunctionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScopeFunctionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            //let to validate inputs
            val name = binding.edtTxtName.text.toString().let { it.trim() }
            val age = binding.edtTxtAge.text.toString().let { it.toIntOrNull() }
            val city = binding.edtTxtCity.text.toString().let { it.trim() }
            if (name.isNullOrEmpty() || age == null || city.isNullOrEmpty()) {
                binding.txtViewResult.text = "Enter valid details!"
            } else {
                //create user object
                val user = User(name, age, city)

                //process and format data using run
                val formattedProfile = user.run {
                    "Name: $name\nAge: $age\nCity: $city"
                }

                //update repeated ui using with
                with(binding.txtViewResult) {
                    text = formattedProfile
                    textSize = 20f
                }

                //use 'also' to debug or log details
                user.also {
                    println("User details: $it")
                }
            }
        }
    }
}
