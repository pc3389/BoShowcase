package com.example.boshowcase.ui.model

/**
 * Profile for Profile Screen.
 * @param name name
 * @param title title
 * @param bio Short description
 * @param skills Skills
 * @param experiences Experiences
 */
data class Profile(
    val name: String,
    val title: String,
    val bio: String,
    val skills: List<String>,
    val experiences: List<String>
)