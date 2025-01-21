package com.example.boshowcase.ui.profilescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boshowcase.ui.model.Profile
import com.example.boshowcase.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * View Model for Profile Screen
 *
 * @param repository Repository to handle GetProfile call.
 */
class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> get() = _profile

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            try {
                // Fetch profile from repository
                val profile = repository.getProfile()
                // Update the state flow
                _profile.value = profile
            } catch (e: Exception) {
                // Handle errors if any (e.g., log or show error state)
                _profile.value = null
            }
        }
    }
}