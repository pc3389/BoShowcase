package com.example.boshowcase.repository

import com.example.boshowcase.ui.model.Project

/**
 * Repository for Portfolio call.
 */
class PortfolioRepository {
    // Mock portfolio data
    fun getProjects(): List<Project> {
        return listOf(
            Project(
                title = "BoShowcase",
                description = "An Android app to showcase my professional portfolio.",
                technologies = listOf("Kotlin", "Jetpack Compose", "MVVM"),
                githubLink = "https://github.com/pc3389/BoShowcase"
            ),
            Project(
                title = "Weather App",
                description = "A simple weather app using REST APIs.",
                technologies = listOf("Kotlin", "Retrofit", "LiveData"),
                githubLink = "https://github.com/pc3389/WeatherApp"
            )
        )
    }
}