package com.example.boshowcase.ui.settingsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boshowcase.data.preference.PreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val isDarkTheme: Flow<Boolean> = preferencesManager.isDarkTheme

    fun setDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            try {
                preferencesManager.setDarkTheme(enabled)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}