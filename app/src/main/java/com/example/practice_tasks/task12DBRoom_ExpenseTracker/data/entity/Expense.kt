package com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    indices = [
        Index("date"),
        Index("categoryId"),
        Index("paymentMethod")
    ],
    foreignKeys = [
        ForeignKey(
            entity = category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PaymentMethod :: class,
            parentColumns = ["methodId"],
            childColumns = ["paymentMethod"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int = 0,
    val title: String,
    val amount: Double,
    val date: Date,
    val categoryId: Int,
    val paymentMethod: Int
)
