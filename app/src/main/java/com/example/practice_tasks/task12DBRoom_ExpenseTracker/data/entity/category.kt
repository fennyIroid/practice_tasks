package com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity (
    indices = [Index(value = ["name"], unique = true)]
)
data class category (
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int = 0,
    val name: String
)