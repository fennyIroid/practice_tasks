package com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PaymentMethod (
    @PrimaryKey(autoGenerate = true)
    val methodId: Int = 0,
    val type: String
)