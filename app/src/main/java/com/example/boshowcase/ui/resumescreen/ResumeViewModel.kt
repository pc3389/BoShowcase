package com.example.boshowcase.ui.resumescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boshowcase.ui.model.Resume
import com.example.boshowcase.repository.ResumeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * View Model for Resume screen.
 *
 * @param repository Repository for get resume call
 */
class ResumeViewModel(private val repository: ResumeRepository) : ViewModel() {

    private val _resume = MutableStateFlow<Resume?>(null)
    val resume: StateFlow<Resume?> get() = _resume.asStateFlow()

    init {
        loadResume()
    }

    /**
     * Load resume from Repository and
     */
    fun loadResume() {
        viewModelScope.launch {
            try {
                // Fetch profile from repository
                val resume = repository.getResume()
                // Update the state flow
                _resume.value = resume
            } catch (e: Exception) {
                // Handle errors if any (e.g., log or show error state)
                _resume.value = Resume()
            }
        }
    }
}
