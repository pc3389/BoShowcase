package com.example.boshowcase.ui.resumescreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.boshowcase.ui.model.Certification
import com.example.boshowcase.ui.model.Education
import com.example.boshowcase.ui.model.Experience
import com.example.boshowcase.ui.model.Resume
import com.example.boshowcase.repository.ResumeRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
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
class ResumeViewModelUnitTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: ResumeRepository
    private lateinit var viewModel: ResumeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = ResumeViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadResume updates resume state`() = runTest {
        // Arrange
        val mockResume = Resume(
            experiences = listOf(
                Experience("Android Developer", "TD Bank", "Jan 2020 - Dec 2022", "Built modern Android apps."),
            ),
            education = listOf(
                Education("B.Sc. Computer Science", "University of Technology", "2014 - 2018")
            ),
            certifications = listOf(
                Certification("Kotlin Certification", "JetBrains", "2021")
            ),
            linkedIn = "https://www.linkedin.com/test"
        )
        coEvery { repository.getResume() } returns mockResume

        // Act
        viewModel.loadResume()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val result = viewModel.resume.first()
        assertEquals(mockResume, result)
    }

    @Test
    fun `loadResume handles empty resume`() = runTest {
        // Arrange
        val emptyResume = Resume(
            experiences = emptyList(),
            education = emptyList(),
            certifications = emptyList(),
            linkedIn = ""
        )
        coEvery { repository.getResume() } throws RuntimeException("Repository error")

        // Act
        viewModel.loadResume()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val result = viewModel.resume.first()
        assertEquals(emptyResume, result)
    }
}