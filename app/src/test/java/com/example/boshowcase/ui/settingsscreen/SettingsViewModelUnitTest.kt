package com.example.boshowcase.ui.settingsscreen

import com.example.boshowcase.TestCoroutineRule
import com.example.boshowcase.data.preference.PreferencesManager
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var viewModel: SettingsViewModel

    @get:Rule
    val dispatcherRule = TestCoroutineRule()

    @Before
    fun setUp() {
        preferencesManager = mockk(relaxed = true) // Mock PreferencesManager
        viewModel = SettingsViewModel(preferencesManager)
    }

    @Test
    fun `setDarkTheme should call preferencesManager setDarkTheme`() = dispatcherRule.runTest {
        // Arrange
        val darkThemeValue = true
        coEvery { preferencesManager.setDarkTheme(darkThemeValue) } just Runs

        // Act
        viewModel.setDarkTheme(darkThemeValue)
        dispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify { preferencesManager.setDarkTheme(darkThemeValue) }
    }
}