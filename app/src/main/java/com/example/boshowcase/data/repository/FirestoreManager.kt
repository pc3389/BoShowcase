package com.example.boshowcase.data.repository

import com.example.boshowcase.ui.model.Profile
import com.example.boshowcase.ui.model.Project
import com.example.boshowcase.ui.model.Resume
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

/**
 * FirestoreManager.
 */
class FirestoreManager {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    /**
     * Saves the profile data to the Firestore database.
     *
     * @param profile A map representing the profile fields and their values.
     * @throws Exception if the save operation fails.
     */
    suspend fun saveProfile(profile: Profile) {
        db.collection("profile").document("data").set(profile).await()
    }

    /**
     * Fetches the profile data from the Firestore database.
     *
     * @return A map representing the profile data, or null if the document doesn't exist.
     * @throws Exception if the fetch operation fails.
     */
    suspend fun getProfile(): Profile? {
        val snapshot = db.collection("profile").document("data").get().await()
        return snapshot.toObject(Profile::class.java)
    }

    /**
     * Saves a project to the portfolio sub-collection in Firestore.
     *
     * @param project A map representing the project fields and their values.
     * @throws Exception if the save operation fails.
     */
    suspend fun saveProject(project: Project) {
        db.collection("profile").document("data")
            .collection("portfolio").document(project.id).set(project).await()
    }

    /**
     * Fetches all portfolio projects from the Firestore database.
     *
     * @return A list of maps, where each map represents a project.
     * @throws Exception if the fetch operation fails.
     */
    suspend fun getProjects(): List<Project> {
        val snapshot = db.collection("profile").document("data")
            .collection("portfolio").get().await()
        return snapshot.documents.mapNotNull { it.toObject<Project>() }
    }

    /**
     * Uploads a resume file to Firebase Storage.
     *
     * @param uri The URI of the file to be uploaded.
     * @return The download URL of the uploaded resume.
     * @throws Exception if the upload operation fails.
     */
    suspend fun uploadResume(uri: android.net.Uri): String {
        val resumeRef = storage.reference.child("resume.pdf")
        resumeRef.putFile(uri).await()
        return resumeRef.downloadUrl.await().toString()
    }

    /**
     * Fetches the resume download URL from Firestore.
     *
     * @return The resume URL as a string, or null if not found.
     * @throws Exception if the fetch operation fails.
     */
    suspend fun getResumeUrl(): String? {
        val snapshot = db.collection("resume").document("data").get().await()
        return snapshot.toObject(Resume::class.java)?.url
    }

    /**
     * Saves the resume URL in Firestore.
     *
     * @param url URL.
     * @throws Exception if the save operation fails.
     */
    suspend fun saveResumeUrl(url: String) {
        db.collection("resume").document("data").set(Resume(url)).await()
    }
}