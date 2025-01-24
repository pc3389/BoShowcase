package com.example.boshowcase.data.repository

import com.example.boshowcase.ui.model.Profile

/**
 * Repository for Profile call.
 */
open class ProfileRepository(private val firestoreManager: FirestoreManager) {
    suspend fun saveProfile(profile: Profile) {
        firestoreManager.saveProfile(profile)
    }

    suspend fun getProfile(): Profile? {
        return firestoreManager.getProfile()
    }
}