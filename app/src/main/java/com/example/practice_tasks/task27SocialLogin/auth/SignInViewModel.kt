package com.example.practice_tasks.task27SocialLogin.auth

import android.content.Intent
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practice_tasks.task19Stateflow.ui.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val googleAuthUiClient: GoogleAuthUiClient): ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state

    init {
        val user = googleAuthUiClient.getSignedInUser()
        if (user != null) {
            _state.value = SignInState(user = user)
        }
    }

    fun onSignInClick(onIntentReady: (Intent) -> Unit){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val result = googleAuthUiClient.signIn()

            result.onSuccess { intent ->
                onIntentReady(intent)
            }.onFailure { e ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown Sign In Error"
                )
            }
        }
    }

    // [MODIFIED] Updated to handle plain Intent for Legacy Sign-In
    fun onSignInResult(intent: Intent) {
        viewModelScope.launch {
            val success = googleAuthUiClient.signInWithIntent(intent)
            if (success) {
                val user = googleAuthUiClient.getSignedInUser()
                
                // [MODIFIED] Crucial fix: We must pass the 'user' object to the state
                // Otherwise the UI doesn't know we are logged in and wont navigate to Home
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = null,
                    user = user
                )
            }else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Google Sign In Failed"
                )
            }
        }
    }

    fun signOut() {
        googleAuthUiClient.signOut()
        _state.value = SignInState()
    }
}