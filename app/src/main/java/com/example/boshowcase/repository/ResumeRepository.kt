package com.example.boshowcase.repository

import com.example.boshowcase.ui.model.Certification
import com.example.boshowcase.ui.model.Education
import com.example.boshowcase.ui.model.Experience
import com.example.boshowcase.ui.model.Resume

/**
 * Repository to handle get resume call.
 */
class ResumeRepository {
    fun getResume(): Resume {
        return Resume(
            experiences = listOf(
                Experience(
                    title = "Android Developer",
                    company = "TD Bank",
                    duration = "June 2021 - Jul 2024",
                    description = "Developed modern Android applications using Jetpack Compose, MVVM, and other cutting-edge technologies."
                )
            ),
            education = listOf(
                Education(
                    degree = "Bachelor of Applied Science, Mechanical Engineering",
                    institution = "University of Waterloo",
                    year = "2010 - 2017"
                )
            ),
            certifications = listOf(
                Certification(
                    name = "Android Developer Nanodegree",
                    issuer = "Udacity",
                    year = "2020"
                )
            ),
            linkedIn = "https://www.linkedin.com/in/boyoung-song/" // Mock LinkedIn URL
        )
    }
}