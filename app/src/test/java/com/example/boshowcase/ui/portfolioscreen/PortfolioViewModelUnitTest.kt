package com.example.boshowcase.ui.portfolioscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.boshowcase.TestCoroutineRule
import com.example.boshowcase.ui.model.Project
import com.example.boshowcase.data.repository.PortfolioRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PortfolioViewModelTest {

    private lateinit var repository: PortfolioRepository
    private lateinit var viewModel: PortfolioViewModel

    @get:Rule
    val dispatcherRule = TestCoroutineRule()


    @Before
    fun setUp() {
        // Create a relaxed mock for the repository
        repository = mockk(relaxed = true)
        viewModel = PortfolioViewModel(repository)
    }

    @Test
    fun `loadProjects should fetch projects from repository and update state`() = dispatcherRule.runTest {
        // Arrange
        val mockProjects = listOf(
            Project(id = "1", title = "Project 1", description = "Description 1"),
            Project(id = "2", title = "Project 2", description = "Description 2")
        )
        coEvery { repository.getProjects() } returns mockProjects

        // Act
        viewModel.loadProjects()
        dispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertEquals(mockProjects, viewModel.projects.first()) // Verify state is updated
        coVerify { repository.getProjects() } // Verify repository method is called
    }

    @Test
    fun `loadProjects should handle exception and set default project`() = dispatcherRule.runTest {
        // Arrange
        coEvery { repository.getProjects() } throws Exception("Failed to fetch projects")

        // Act
        viewModel.loadProjects()
        dispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.projects.first()
        assertEquals(1, state!!.size) // State contains one default project
        assertEquals(Project(), state[0]) // Default project is used
        coVerify { repository.getProjects() } // Verify repository method is called
    }

    @Test
    fun `saveProject should call repository and reload projects`() = dispatcherRule.runTest {
        // Arrange
        val projectToSave = Project(id = "3", title = "New Project", description = "New Description")
        val updatedProjects = listOf(
            Project(id = "1", title = "Project 1", description = "Description 1"),
            projectToSave
        )
        coEvery { repository.saveProject(projectToSave) } just Runs
        coEvery { repository.getProjects() } returns updatedProjects

        // Act
        viewModel.saveProject(projectToSave)
        dispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertEquals(updatedProjects, viewModel.projects.first()) // Verify state is updated
        coVerifySequence { // Verify calls happen in sequence
            repository.getProjects()
            repository.saveProject(projectToSave)
            repository.getProjects()
        }
    }

    @Test
    fun `findProjectById should return the correct project`() = dispatcherRule.runTest {
        // Arrange
        val mockProjects = listOf(
            Project(id = "1", title = "Project 1", description = "Description 1"),
            Project(id = "2", title = "Project 2", description = "Description 2")
        )
        coEvery { repository.getProjects() } returns mockProjects

        // Act
        viewModel.loadProjects()
        dispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.findProjectById("2")

        // Assert
        assertEquals(mockProjects[1], result) // Verify correct project is returned
    }

    @Test
    fun `findProjectById should return null if project not found`() = dispatcherRule.runTest {
        // Arrange
        val mockProjects = listOf(
            Project(id = "1", title = "Project 1", description = "Description 1")
        )
        coEvery { repository.getProjects() } returns mockProjects

        // Act
        viewModel.loadProjects()
        val result = viewModel.findProjectById("non-existent-id")

        // Assert
        assertEquals(null, result) // Verify null is returned
    }
}