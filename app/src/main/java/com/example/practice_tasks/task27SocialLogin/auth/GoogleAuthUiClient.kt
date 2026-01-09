package com.example.practice_tasks.task27SocialLogin.auth

import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val context: Context
) {

    private val auth: FirebaseAuth =
        FirebaseAuth.getInstance()

    suspend fun signIn(): Result<Intent> {
        return try {
            // [MODIFIED] Added resource check to ensure google-services.json is valid
            // This prevents the "silent failure" where the app would just crash or do nothing
            val clientRes = context.resources.getIdentifier(
                "default_web_client_id",
                "string",
                context.packageName
            )
            if (clientRes == 0) {
                val msg = "Resource default_web_client_id not found. Check google-services.json"
                android.util.Log.e("GoogleAuthUiClient", msg)
                return Result.failure(Exception(msg))
            }
            val clientId = context.getString(clientRes)

            // [MODIFIED] Switched to Legacy Google Sign-In API.
            // Why: "One Tap" was causing "Cannot find matching credential" errors.
            // This old-school method reliably opens the Google Account Chooser popup.
            val gso = com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(
                com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
            )
                .requestIdToken(clientId)
                .requestEmail()
                .build()

            val googleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(context, gso)
            // Force sign out to prompt account chooser every time we click the button
            googleSignInClient.signOut()
            
            Result.success(googleSignInClient.signInIntent)
        }catch (e: Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun signInWithIntent(intent: Intent): Boolean {
        return try {
             // [MODIFIED] Extract account from Intent using Legacy API helper
            val task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(intent)
            val account = task.await()
            val googleIdToken = account.idToken ?: return false

             // Exchange Google Token for Firebase Credential
             val firebaseCredential =
                GoogleAuthProvider.getCredential(googleIdToken, null)
             auth.signInWithCredential(firebaseCredential).await()
             true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getSignedInUser() = auth.currentUser

    fun signOut(){
        auth.signOut()
        try {
            val gso = com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(
                com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
            ).build()
            com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(context, gso).signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
