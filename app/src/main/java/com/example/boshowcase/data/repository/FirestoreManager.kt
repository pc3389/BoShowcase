package com.example.boshowcase.data.repository

import android.net.Uri
import com.example.boshowcase.ui.model.Profile
import com.example.boshowcase.ui.model.Project
import com.example.boshowcase.ui.model.Resume
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

/**
 * FirestoreManager - Manages global shared data for all users.
 */
class FirestoreManager {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    /**
     * Saves profile data in a shared location.
     */
    suspend fun saveProfile(profile: Profile) {
        db.collection("app_data").document("common_info").set(profile).await()
    }

    /**
     * Fetches the shared profile data.
     */
    suspend fun getProfile(): Profile? {
        val snapshot = db.collection("app_data").document("common_info").get().await()
        return snapshot.toObject(Profile::class.java)
    }

    /**
     * Saves a project to a shared portfolio.
     */
    suspend fun saveProject(project: Project) {
        db.collection("app_data").document("common_info")
            .collection("portfolio").document(project.id).set(project).await()
    }

    /**
     * Fetches all portfolio projects for all users.
     */
    suspend fun getProjects(): List<Project> {
        val snapshot = db.collection("app_data").document("common_info")
            .collection("portfolio").get().await()
        return snapshot.documents.mapNotNull { it.toObject<Project>() }
    }

    /**
     * Uploads a resume file to Firebase Storage.
     */
    suspend fun uploadResume(uri: Uri): String {
        val resumeRef = storage.reference.child("shared_resume.pdf")
        resumeRef.putFile(uri).await()
        return resumeRef.downloadUrl.await().toString()
    }

    /**
     * Fetches the shared resume download URL.
     */
    suspend fun getResumeUrl(): String? {
        val snapshot = db.collection("app_data").document("common_info").get().await()
        return snapshot.toObject(Resume::class.java)?.url
    }

    /**
     * Saves the resume URL in Firestore.
     */
    suspend fun saveResumeUrl(url: String) {
        db.collection("app_data").document("common_info").set(Resume(url)).await()
    }
}