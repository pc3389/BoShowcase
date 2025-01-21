package com.example.boshowcase.ui.settingsscreen

import com.example.boshowcase.data.preference.PreferencesManager
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {
//
//    private val testDispatcher = StandardTestDispatcher()
//    private lateinit var preferencesManager: PreferencesManager
//    private lateinit var viewModel: SettingsViewModel
//
//    @Before
//    fun setUp() {
//        Dispatchers.setMain(testDispatcher) // Set test dispatcher
//        preferencesManager = mockk() // Mock PreferencesManager
//        viewModel = SettingsViewModel(preferencesManager)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain() // Reset dispatcher after tests
//    }
//
//    @Test
//    fun `isDarkTheme emits false by default`() = runTest {
//        // Arrange
//        coEvery { preferencesManager.isDarkTheme } returns flowOf(false)
//
//        // Act
//        val result = viewModel.isDarkTheme
//
//        // Assert
//        assertEquals(false, result)
//    }
//
//    @Test
//    fun `isDarkTheme emits true when enabled`() = runTest {
//        // Arrange
//        every { preferencesManager.isDarkTheme } returns flowOf(true)
//
//        // Act
//        val result = viewModel.isDarkTheme
//
//        // Assert
//        result.collect {
//            assertEquals(true, it)
//        }
//    }
//
//    @Test
//    fun `setDarkTheme calls PreferencesManager`() = runTest {
//        // Arrange
//        coEvery { preferencesManager.setDarkTheme(true) } just Runs
//
//        // Act
//        viewModel.setDarkTheme(true)
//        testDispatcher.scheduler.advanceUntilIdle()
//
//        // Assert
//        coVerify { preferencesManager.setDarkTheme(true) }
//    }
//
//    @Test
//    fun `setDarkTheme sets dark mode to false`() = runTest {
//        // Arrange
//        coEvery { preferencesManager.setDarkTheme(false) } just Runs
//
//        // Act
//        viewModel.setDarkTheme(false)
//        testDispatcher.scheduler.advanceUntilIdle()
//
//        // Assert
//        coVerify { preferencesManager.setDarkTheme(false) }
//    }
}