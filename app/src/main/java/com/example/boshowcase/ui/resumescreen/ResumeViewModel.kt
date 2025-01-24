package com.example.boshowcase.ui.resumescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boshowcase.data.repository.ResumeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * View Model for Resume screen.
 *
 * @param repository Repository for get resume call
 */
class ResumeViewModel(private val repository: ResumeRepository) : ViewModel() {

    private val _resumeUrl = MutableStateFlow<String?>(null)
    val resumeUrl = _resumeUrl.asStateFlow()

    /**
     * Load resume from Repository and
     */
    fun loadResumeUrl() {
        viewModelScope.launch {
            try {
                _resumeUrl.value = repository.getResumeUrl()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun uploadResume(uri: android.net.Uri) {
        viewModelScope.launch {
            try {
                val url = repository.uploadResume(uri)
                repository.saveResumeUrl(url)
                _resumeUrl.value = url
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
