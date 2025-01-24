package com.example.boshowcase.data.repository

import com.example.boshowcase.ui.model.Resume

/**
 * Repository to handle get resume call.
 */
class ResumeRepository(private val firestoreManager: FirestoreManager) {
    suspend fun uploadResume(uri: android.net.Uri): String {
        return firestoreManager.uploadResume(uri)
    }

    suspend fun getResumeUrl(): String? {
        return firestoreManager.getResumeUrl()
    }

    suspend fun saveResumeUrl(url: String) {
        firestoreManager.saveResumeUrl(url)
    }
}