package com.example.boshowcase.ui.portfolioscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.boshowcase.ui.model.Project
import com.example.boshowcase.repository.PortfolioRepository
import io.mockk.coEvery
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

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: PortfolioRepository
    private lateinit var viewModel: PortfolioViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = PortfolioViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProjects updates projects state on success`() = runTest {
        // Arrange
        val mockProjects = listOf(
            Project("BoShowcase", "Portfolio app", listOf("Kotlin", "Compose"), "https://github.com/bo/showcase"),
            Project("WeatherApp", "Weather tracker", listOf("Kotlin", "Retrofit"), "https://github.com/bo/weatherapp")
        )
        coEvery { repository.getProjects() } returns mockProjects

        // Act
        viewModel.loadProjects()
        testDispatcher.scheduler.advanceUntilIdle() // Ensure coroutine runs

        // Assert
        val result = viewModel.projects.first()
        assertEquals(mockProjects, result)
    }

    @Test
    fun `loadProjects handles error and updates projects state to empty`() = runTest {
        // Arrange
        coEvery { repository.getProjects() } throws RuntimeException("Repository error")

        // Act
        viewModel.loadProjects()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val result = viewModel.projects.first()
        assertTrue(result.first().title.isEmpty())
    }
}