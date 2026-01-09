package com.example.practice_tasks.task3Ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.practice_tasks.databinding.ActivityDataBindingBinding
import com.example.practice_tasks.databinding.ActivityDynamicListBinding
import java.util.ArrayList

class DynamicListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDynamicListBinding
    private lateinit var adapter: UserAdapter
    private val userList = ArrayList<userModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDynamicListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCitySpinner()

        binding.btnAddUser.setOnClickListener {
            addUser()
        }

        binding.btnShowList.setOnClickListener {
            showList()
        }

    }

    private fun setupCitySpinner() {
        val cities = listOf<String>("Surat", "Mumbai", "Delhi", "Bangalore", "Hyderabad", "Pune")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCity.adapter = adapter
    }

    private fun addUser() {

        val name = binding.edtTxtName.text.toString().trim()
        val ageString = binding.edtTxtAge.text.toString().trim()
        val city = binding.spinnerCity.selectedItem.toString()

        val age = ageString.toInt()
        val gender = if (binding.rbMale.isChecked) "Male" else "Female"
        val hobbies = mutableListOf<String>()
        if (binding.cbSports.isChecked) hobbies.add("Sports")
        if (binding.cbTravel.isChecked) hobbies.add("Travel")

        val isActive = binding.switchActive.isChecked
        val rating = binding.ratingBar.rating

        val user = userModel (
            name = name,
            age = age,
            city = city,
            gender = gender,
            hobbies = hobbies,
            isActive = isActive,
            rating = rating
        )

        userList.add(user)
        Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()

        clearFields()
    }

    private fun showList() {
        val intent = Intent(this, UserListActivity::class.java)
        intent.putExtra("users",userList)
        startActivity(intent)
    }

    private fun clearFields() {
        binding.edtTxtName.text.clear()
        binding.edtTxtAge.text.clear()
        binding.spinnerCity.setSelection(0)
        binding.radioGroup.clearCheck()
        binding.cbSports.isChecked = false
        binding.cbTravel.isChecked = false
        binding.switchActive.isChecked = false
        binding.ratingBar.rating = 0f
    }
}