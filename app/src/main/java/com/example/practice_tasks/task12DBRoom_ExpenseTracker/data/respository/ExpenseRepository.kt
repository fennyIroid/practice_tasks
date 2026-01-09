package com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.respository

import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.dao.ExpenseDao
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity.Expense

class ExpenseRepository (private val dao: ExpenseDao){

    fun getAllExpenses() = dao.getAllExpenses()

    fun getSummary() = dao.getExpenseSummary()

    suspend fun getAllCategories() = dao.getAllCategories()

    suspend fun getAllPaymentMethods() = dao.getAllPaymentMethods()


    suspend fun addExpense(expense: Expense) {
        dao.insertExpense(expense)
    }
}