package com.example.boshowcase.data.repository

import com.example.boshowcase.ui.model.Project

/**
 * Repository for Portfolio call.
 */
class PortfolioRepository(private val firestoreManager: FirestoreManager) {
    suspend fun saveProject(project: Project) {
        firestoreManager.saveProject(project)
    }

    suspend fun getProjects(): List<Project> {
        return firestoreManager.getProjects()
    }
}