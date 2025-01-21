package com.example.boshowcase.ui.mainscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.boshowcase.ui.model.Project
import com.example.boshowcase.ui.navigation.BottomNavigationBar
import com.example.boshowcase.ui.portfolioscreen.PortfolioScreen
import com.example.boshowcase.ui.profilescreen.ProfileScreen
import com.example.boshowcase.ui.projectdetailscreen.ProjectDetailsScreen
import com.example.boshowcase.repository.PortfolioRepository
import com.example.boshowcase.ui.resumescreen.ResumeScreen
import com.example.boshowcase.ui.settingsscreen.SettingsScreen

/**
 * Main Screen handling bottom navigation.
 */
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
            composable("portfolio") {
                PortfolioScreen(
                    onProjectClick = { project ->
                        bottomNavController.navigate("projectDetails/${project.title}")
                    }
                )
            }
            // Project Details Screen
            composable(
                route = "projectDetails/{title}",
                arguments = listOf(navArgument("title") { type = NavType.StringType })
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val project = findProjectByTitle(title) // Fetch project by title
                project?.let { ProjectDetailsScreen(it) }
            }
            composable("resume") { ResumeScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}

fun findProjectByTitle(title: String): Project? {
    // Mock project list for demonstration
    val projects = PortfolioRepository().getProjects()
    return projects.find { it.title == title }
}