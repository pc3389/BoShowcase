package com.example.boshowcase.ui.model

data class Project(
    val title: String = "",
    val description: String = "",
    val technologies: List<String> = listOf(),
    val githubLink: String = ""
)