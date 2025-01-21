package com.example.boshowcase.ui.profilescreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.boshowcase.ui.model.Profile
import com.example.boshowcase.repository.ProfileRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockRepository: ProfileRepository
    private lateinit var viewModel: ProfileViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk<ProfileRepository>()
        viewModel = ProfileViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProfile updates profile state`() = runTest {
        // Arrange
        val mockProfile = Profile(
            name = "Bo",
            title = "Android Developer",
            bio = "Passionate about Android development.",
            skills = listOf("Kotlin", "Jetpack Compose"),
            experiences = listOf("3 years at TD Bank", "Built 5+ apps")
        )
        coEvery { mockRepository.getProfile() } returns mockProfile

        // Act
        viewModel.loadProfile()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val result = viewModel.profile.value
        assertEquals(mockProfile, result)
    }
}