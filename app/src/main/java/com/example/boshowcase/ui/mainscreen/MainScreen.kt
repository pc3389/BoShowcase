package com.example.boshowcase.ui.mainscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.boshowcase.ui.navigation.BottomNavigationBar
import com.example.boshowcase.ui.portfolioscreen.PortfolioScreen
import com.example.boshowcase.ui.profilescreen.ProfileScreen
import com.example.boshowcase.ui.resumescreen.ResumeScreen
import com.example.boshowcase.ui.settingsscreen.SettingsScreen

@Composable
fun MainScreen() {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(bottomNavController) }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "profile",
            Modifier.padding(innerPadding)
        ) {
            composable("profile") { ProfileScreen() }
            composable("portfolio") { PortfolioScreen() }
            composable("resume") { ResumeScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}