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
import com.example.boshowcase.data.repository.FirestoreManager
import com.example.boshowcase.ui.model.Project
import com.example.boshowcase.ui.navigation.BottomNavigationBar
import com.example.boshowcase.ui.portfolioscreen.PortfolioScreen
import com.example.boshowcase.ui.profilescreen.ProfileScreen
import com.example.boshowcase.ui.projectdetailscreen.ProjectDetailsScreen
import com.example.boshowcase.data.repository.PortfolioRepository
import com.example.boshowcase.data.repository.ProfileRepository
import com.example.boshowcase.data.repository.ResumeRepository
import com.example.boshowcase.ui.portfolioscreen.PortfolioViewModel
import com.example.boshowcase.ui.profilescreen.ProfileViewModel
import com.example.boshowcase.ui.resumescreen.ResumeScreen
import com.example.boshowcase.ui.resumescreen.ResumeViewModel
import com.example.boshowcase.ui.settingsscreen.SettingsScreen

private val portfolioViewModel by lazy {
    PortfolioViewModel(
        PortfolioRepository(
            firestoreManager = FirestoreManager()
        )
    )
}
private val profileViewModel by lazy {
    ProfileViewModel(
        ProfileRepository(
            FirestoreManager()
        )
    )
}

private val resumeViewModel by lazy {
    ResumeViewModel(
        ResumeRepository(
            FirestoreManager()
        )
    )
}

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
            composable("profile") {
                ProfileScreen(profileViewModel)
            }
            composable("portfolio") {
                PortfolioScreen(
                    viewModel = portfolioViewModel,
                    onProjectClick = { project ->
                        bottomNavController.navigate("projectDetails/${project.id}")
                    }
                )
            }
            // Project Details Screen
            composable(
                route = "projectDetails/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val projectId = backStackEntry.arguments?.getString("id") ?: ""
                val project = portfolioViewModel.findProjectById(projectId) // Fetch project by title
                project?.let { ProjectDetailsScreen(it) }
            }
            composable("resume") {
                ResumeScreen(resumeViewModel)
            }
            composable("settings") { SettingsScreen() }
        }
    }
}