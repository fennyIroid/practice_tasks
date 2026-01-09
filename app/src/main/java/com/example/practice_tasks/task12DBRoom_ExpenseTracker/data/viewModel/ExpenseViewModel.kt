package com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity.Expense
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity.PaymentMethod
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity.category
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.respository.ExpenseRepository
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback

class ExpenseViewModel (private val repository: ExpenseRepository): ViewModel() {

    val expenses = repository.getAllExpenses().distinctUntilChanged()

    val summary = repository.getSummary().distinctUntilChanged()

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repository.addExpense(expense)
        }
    }

    fun getCategories(callback: (List<category>) -> Unit) {
        viewModelScope.launch {
            callback(repository.getAllCategories())
        }
    }

    fun getPaymentMethods(callback: (List<PaymentMethod>) -> Unit) {
        viewModelScope.launch {
            callback(repository.getAllPaymentMethods())
        }
    }
}

class ExpenseViewModelFactory(private val repository: ExpenseRepository) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}