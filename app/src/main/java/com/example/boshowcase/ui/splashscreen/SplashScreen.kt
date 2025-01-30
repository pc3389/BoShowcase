package com.example.boshowcase.ui.splashscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

/**
 * Splash Screen to show before entering main screen.
 *
 * @param navController Navigation controller for Splash Screen and Main Screen
 */
@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        // Simulate a brief loading or branding delay
        delay(2000)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            // No user -> go to Login
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            // Already signed in -> go to Main
            navController.navigate("main") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to BoShowcase",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview
@Composable
fun Preview() {
    SplashScreen(rememberNavController())
}