package com.example.boshowcase.ui.model

/**
 * Resume Data Model
 *
 * @param experiences experiences
 * @param education education
 * @param certifications certifications
 * @param linkedIn LinkedIn URL
 */
data class Resume(
    val experiences: List<Experience> = listOf(),
    val education: List<Education> = listOf(),
    val certifications: List<Certification> = listOf(),
    val linkedIn: String = ""
)

/**
 * Experience Data Model
 *
 * @param title Title of the experience
 * @param company Company
 * @param duration duration of work experience
 * @param description description of the work
 */
data class Experience(
    val title: String,
    val company: String,
    val duration: String, // e.g., "Jan 2020 - Dec 2022"
    val description: String
)

/**
 * Education Data Model.
 *
 * @param degree degree
 * @param institution institution
 * @param year year
 */
data class Education(
    val degree: String,
    val institution: String,
    val year: String // e.g., "2018 - 2020"
)

/**
 * Certification Data Model
 *
 * @param name name of the certification
 * @param issuer issuer
 * @param year year
 */
data class Certification(
    val name: String,
    val issuer: String,
    val year: String
)