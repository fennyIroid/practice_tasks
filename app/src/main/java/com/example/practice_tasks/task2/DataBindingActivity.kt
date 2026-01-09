package com.example.practice_tasks.task2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.practice_tasks.R
import com.example.practice_tasks.databinding.ActivityDataBindingBinding

class DataBindingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataBindingBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
