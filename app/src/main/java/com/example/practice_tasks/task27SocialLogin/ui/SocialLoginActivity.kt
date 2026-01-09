package com.example.practice_tasks.task27SocialLogin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practice_tasks.task27SocialLogin.auth.GoogleAuthUiClient
import com.example.practice_tasks.task27SocialLogin.auth.SignInViewModel
import com.example.practice_tasks.task27SocialLogin.ui.ui.theme.Practice_tasksTheme

class SocialLoginActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        val goggleAuthUiClient = GoogleAuthUiClient(applicationContext)
        val viewModel = SignInViewModel(goggleAuthUiClient)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practice_tasksTheme {

                val state by viewModel.state.collectAsStateWithLifecycle()

                // Check if user is logged in
                if (state.user == null) {
                    SignInScreen(
                        viewModel = viewModel,
                        onSignInSuccess = {
                             // Optional: Handle success callback if needed
                        }
                    )
                } else {
                    HomeScreen(
                        user = state.user!!,
                        onLogout = {
                            viewModel.signOut()
                        }
                    )
                }

            }
        }
    }
}
