package com.example.boshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.boshowcase.ui.mainscreen.MainScreen
import com.example.boshowcase.ui.profilescreen.ProfileScreen
import com.example.boshowcase.ui.splashscreen.SplashScreen
import com.example.boshowcase.ui.theme.BoShowcaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoShowcaseApp()
        }
    }
}

@Composable
fun BoShowcaseApp() {
    val navController = rememberNavController()
    BoShowcaseTheme {
        AppNavigation(navController)
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("main") { MainScreen() }
    }
}