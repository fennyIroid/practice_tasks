package com.example.practice_tasks.task12DBRoom_ExpenseTracker.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practice_tasks.R
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity.Expense
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.viewModel.ExpenseViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import java.util.Date

import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.db.ExpenseDatabase
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.respository.ExpenseRepository
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.viewModel.ExpenseViewModelFactory

class AddExpenseActivity : AppCompatActivity() {

    private val viewModel: ExpenseViewModel by viewModels {
        val database = ExpenseDatabase.getDatabase(application)
        val repository = ExpenseRepository(database.expenseDao())
        ExpenseViewModelFactory(repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_expense)

        val etTitle = findViewById<TextInputEditText>(R.id.etExpenseTitle)
        val etAmount = findViewById<TextInputEditText>(R.id.etAmount)
        val chipCategory = findViewById<ChipGroup>(R.id.chipCategory)
        val spPaymentMethod = findViewById<AutoCompleteTextView>(R.id.spPaymentMethod)
        val btnSave = findViewById<MaterialButton>(R.id.btnSave)

        viewModel.getCategories { categories ->
            categories.forEach { category ->
                val chip = Chip(this).apply {
                    text = category.name
                    isCheckable = true
                    tag = category.categoryId
                }
                chipCategory.addView(chip)
            }
        }

        var selectedPaymentMethodId: Int = -1
        viewModel.getPaymentMethods { methods ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, methods.map { it.type })
            spPaymentMethod.setAdapter(adapter)
            spPaymentMethod.setOnItemClickListener { _, _, position, _ ->
                selectedPaymentMethodId = methods[position].methodId
            }
        }

        btnSave.setOnClickListener {
            val title = etTitle.text?.toString()?.trim()
            val amount = etAmount.text?.toString()

            if (title.isNullOrEmpty()) {
                etTitle.error = "Enter Title"
                return@setOnClickListener
            }

            if (amount.isNullOrEmpty()) {
                etAmount.error = "Enter Amount"
                return@setOnClickListener
            }

            val selectedChipId = chipCategory.checkedChipId
            if (selectedChipId == -1) {
                Toast.makeText(this,"Select Category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedChip = chipCategory.findViewById<Chip>(selectedChipId)
            val categoryId = selectedChip.tag as Int

            if (selectedPaymentMethodId == -1) {
                Toast.makeText(this,"Select Payment Method", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val expense = Expense(
                title = title,
                amount = amount.toDouble(),
                date = Date(),
                categoryId = categoryId,
                paymentMethod = selectedPaymentMethodId
            )

            viewModel.addExpense(expense)
            Toast.makeText(this, "Expense Added", Toast.LENGTH_SHORT).show()
            finish()
            }
        }
}