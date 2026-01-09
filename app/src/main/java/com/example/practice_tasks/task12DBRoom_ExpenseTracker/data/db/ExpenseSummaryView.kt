package com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.db

import androidx.room.DatabaseView

@DatabaseView (
    """
            SELECT
                category.name as categoryName,
                SUM(Expense.amount) as totalAmount
            FROM Expense
            INNER JOIN category
            ON Expense.categoryId = category.categoryId
            GROUP BY category.name
        """
)

data class ExpenseSummaryView (
    val categoryName: String,
    val totalAmount: Double
)