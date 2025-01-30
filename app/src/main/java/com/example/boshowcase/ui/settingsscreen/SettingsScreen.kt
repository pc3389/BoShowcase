package com.example.boshowcase.ui.settingsscreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.boshowcase.data.preference.PreferencesManager
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = SettingsViewModel(
        PreferencesManager(context = LocalContext.current)
    ),
    navController: NavHostController
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState(initial = false)
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val oneTapClient = remember { Identity.getSignInClient(context) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Theme Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dark Theme",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { viewModel.setDarkTheme(it) }
            )
        }

        HorizontalDivider()

        // About Section
        Text(
            text = "About the App",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Version: 1.0.0",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Developed by Bo",
            style = MaterialTheme.typography.bodyMedium
        )

        // Logout Button
        Button(
            onClick = {
                logoutUser(auth, oneTapClient, navController, context)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Logout", color = MaterialTheme.colorScheme.onError)
        }
    }
}

fun logoutUser(
    auth: FirebaseAuth,
    oneTapClient: SignInClient,
    navController: NavHostController,
    context: Context
) {
    // Firebase Sign-Out (for both Google & Guest users)
    auth.signOut()

    // Google Identity Services Sign-Out (only if user signed in with Google)
    oneTapClient.signOut().addOnCompleteListener {
        // Navigate to login screen after successful logout
        navController.navigate("login") {
            popUpTo("main") { inclusive = true }
        }
        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
    }
}