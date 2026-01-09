package com.example.practice_tasks.task27SocialLogin.ui

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.practice_tasks.task27SocialLogin.auth.SignInViewModel

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    onSignInSuccess: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // [MODIFIED] Changed contract to StartActivityForResult for Legacy Sign-In
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                viewModel.onSignInResult(intent)
            }
        }
    }

    LaunchedEffect(state.user) {
        if (state.user != null){
            onSignInSuccess()
        }
    }

    // [MODIFIED] Added error handling to show Toast on failure
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { error ->
            android.widget.Toast.makeText(context, error, android.widget.Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center ) {
        if (state.isLoading){
            CircularProgressIndicator()
        }else{
            Button(onClick = {
                viewModel.onSignInClick { intent ->
                    // [MODIFIED] Launch intent directly
                    launcher.launch(intent)
                }
            }) {
                Text("Sign in with Google")
            }
        }
    }
}