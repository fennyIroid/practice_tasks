package com.example.practice_tasks.task12DBRoom_ExpenseTracker.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_tasks.R
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.adapter.ExpenseAdapter
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.db.ExpenseDatabase
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.respository.ExpenseRepository
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.viewModel.ExpenseViewModel
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.viewModel.ExpenseViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class ExpenseListActivity : AppCompatActivity() {

    private val viewModel: ExpenseViewModel by viewModels {
        val database = ExpenseDatabase.getDatabase(application)
        val repository = ExpenseRepository(database.expenseDao())
        ExpenseViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_expense_list)

        val recyclerView = findViewById<RecyclerView>(R.id.rvExpenses)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAddExpense)

        val adapter = ExpenseAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            viewModel.expenses.collect { expenses ->
                adapter.submitList(expenses)
            }
        }

        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }
    }
}
