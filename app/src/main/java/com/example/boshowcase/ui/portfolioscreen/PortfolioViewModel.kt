package com.example.boshowcase.ui.portfolioscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boshowcase.ui.model.Project
import com.example.boshowcase.data.repository.PortfolioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * View Model for Portfolio Screen.
 *
 * @param repository Repository to handle Portfolio call.
 */
class PortfolioViewModel(private val repository: PortfolioRepository) : ViewModel() {

    private val _projects = MutableStateFlow<List<Project>?>(null)
    val projects: StateFlow<List<Project>?> get() = _projects

    init {
        loadProjects()
    }

    fun loadProjects() {
        viewModelScope.launch {
            try {
                // Fetch projects from the repository
                val project = repository.getProjects()
                // Update the state flow
                _projects.value = project
            } catch (e: Exception) {
                // Handle errors (set default or empty project)
                _projects.value = listOf(Project())
            }
        }
    }

    fun saveProject(project: Project) {
        viewModelScope.launch {
            try {
                repository.saveProject(project)
                loadProjects() // Refresh the project list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun findProjectById(id: String): Project? {
        return _projects.value?.let { it.find { it.id == id } }
    }
}