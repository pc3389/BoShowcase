package com.example.boshowcase.ui.portfolioscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boshowcase.ui.model.Project
import com.example.boshowcase.ui.repository.PortfolioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * View Model for Portfolio Screen.
 *
 * @param repository Repository to handle Portfolio call.
 */
class PortfolioViewModel(private val repository: PortfolioRepository) : ViewModel() {

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> get() = _projects

    init {
        loadProjects()
    }

    fun loadProjects() {
        viewModelScope.launch {
            try {
                // Fetch profile from repository
                val project = repository.getProjects()
                // Update the state flow
                _projects.value = project
            } catch (e: Exception) {
                // Handle errors if any (e.g., log or show error state)
                _projects.value = listOf(Project())
            }
        }
    }
}