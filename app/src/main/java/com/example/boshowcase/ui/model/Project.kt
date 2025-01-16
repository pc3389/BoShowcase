package com.example.boshowcase.ui.model

/**
 * Project data class for Project Screen.
 *
 * @param title title of the project
 * @param description description of the project
 * @param technologies technologies used on the project
 * @param githubLink github link for the project
 */
data class Project(
    val title: String = "",
    val description: String = "",
    val technologies: List<String> = listOf(),
    val githubLink: String = ""
)