package com.example.boshowcase.ui.model

import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Resume Data Model
 *
 * @param url resume URL
 * @param linkedInUrl LinkedIn URL
 */
@IgnoreExtraProperties
data class Resume(
    val url: String = "",
    val linkedInUrl: String = ""
)