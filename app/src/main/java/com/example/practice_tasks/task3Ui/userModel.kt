package com.example.practice_tasks.task3Ui

import java.io.Serializable

data class userModel (
    val name: String,
    val age: Int,
    val city: String,
    val gender: String,
    val hobbies: List<String>,
    val isActive: Boolean,
    val rating: Float
): Serializable // serializable is used to pass the custom object data through intents between the activities
