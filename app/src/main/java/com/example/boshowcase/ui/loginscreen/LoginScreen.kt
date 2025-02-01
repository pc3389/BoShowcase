package com.example.boshowcase.ui.loginscreen

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.boshowcase.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }

    // Email/Password fields
    var email by remember { mutableStateOf(context.getString(R.string.empty_string)) }
    var password by remember { mutableStateOf(context.getString(R.string.empty_string)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Email/Password Form
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(context.getString(R.string.email)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.email_login_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.email_login_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(context.getString(R.string.sign_in_with_email))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign Up Screen
        Button(
            onClick = { navController.navigate("signup") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(context.getString(R.string.create_new_account))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // One Tap Sign-In
        OneTapSignInButton(navController)

        Spacer(modifier = Modifier.height(16.dp))

        // Guest Login
        Button(
            onClick = {
                auth.signInAnonymously().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.guest_login_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(context.getString(R.string.continue_as_guest))
        }
    }
}

@Composable
fun OneTapSignInButton(navController: NavHostController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    // 1. Build a BeginSignInRequest
    val signInRequest = remember {
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Provide your client ID here:
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    // 2. One Tap Client
    val oneTapClient = remember { Identity.getSignInClient(context) }

    // 3. Launcher for the result of One Tap
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
            val token = credential.googleIdToken
            if (token != null) {
                val firebaseCredential = GoogleAuthProvider.getCredential(token, null)
                auth.signInWithCredential(firebaseCredential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Go to main screen
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.one_tab_sign_in_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.no_id_token),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.one_tab_canceled),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // UI for signing in with Google
    Button(
        onClick = {
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener { result ->
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(result.pendingIntent).build()
                    launcher.launch(intentSenderRequest)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        "${context.getString(R.string.one_tab_sign_in_failed)} ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Sign in with Google")
    }
}