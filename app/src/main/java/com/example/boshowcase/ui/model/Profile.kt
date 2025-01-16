package com.example.boshowcase.ui.model

data class Profile(
    val name: String,
    val title: String,
    val bio: String,
    val skills: List<String>,
    val experiences: List<String>
)