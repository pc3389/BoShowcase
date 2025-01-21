package com.example.boshowcase.repository

import com.example.boshowcase.ui.model.Profile

/**
 * Repository for Profile call.
 */
open class ProfileRepository {
    // Mock profile data
    fun getProfile(): Profile {
        return Profile(
            name = "Bo",
            title = "Android Developer",
            bio = "Passionate about building modern Android applications with Jetpack Compose.",
            skills = listOf("Kotlin", "Jetpack Compose", "MVVM", "Room", "Hilt"),
            experiences = listOf(
                "3 years as Android Developer at TD",
                "Expert in MVVM",
                "Built 5+ Android apps using Kotlin and Jetpack Compose"
            )
        )
    }
}