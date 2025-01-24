package com.example.boshowcase.ui.model

import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Project data class for Project Screen.
 *
 * @param title title of the project
 * @param description description of the project
 * @param technologies technologies used on the project
 * @param githubLink github link for the project
 */
@IgnoreExtraProperties
data class Project(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val technologies: List<String> = emptyList(),
    val githubLink: String = ""
)