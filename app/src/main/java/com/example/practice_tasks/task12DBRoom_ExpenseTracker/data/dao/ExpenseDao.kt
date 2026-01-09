package com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.db.ExpenseSummaryView
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity.Expense
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity.PaymentMethod
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity.category
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense: Expense)

    @Insert
    suspend fun insertCategory(category: category)

    @Insert
    suspend fun insertPaymentMethod(paymentMethod: PaymentMethod)

    @Query("SELECT * FROM expense ORDER by date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM ExpenseSummaryView")
    fun getExpenseSummary(): Flow<List<ExpenseSummaryView>>

    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<category>

    @Query("SELECT * FROM PaymentMethod")
    suspend fun getAllPaymentMethods(): List<PaymentMethod>
}
