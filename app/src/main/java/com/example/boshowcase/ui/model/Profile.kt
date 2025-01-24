package com.example.boshowcase.ui.model

import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Profile for Profile Screen.
 * @param name name
 * @param title title
 * @param bio Short description
 * @param skills Skills
 * @param imageUrl Image URL
 */
@IgnoreExtraProperties
data class Profile(
    val name: String = "",
    val title: String = "",
    val bio: String = "",
    val skills: List<String> = listOf(),
    val imageUrl: String? = null
)