package com.example.practice_tasks.task27SocialLogin.auth

import com.google.firebase.auth.FirebaseUser

data class SignInState (
    val isLoading: Boolean = false,
    val user: FirebaseUser? = null,
    val errorMessage: String? = null
)