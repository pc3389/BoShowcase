package com.example.boshowcase.ui.settingsscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.boshowcase.data.preference.PreferencesManager


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = SettingsViewModel(
        PreferencesManager(context = LocalContext.current)
    )
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState(initial = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Theme Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dark Theme",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { viewModel.setDarkTheme(it) }
            )
        }

        HorizontalDivider()

        // About Section
        Text(
            text = "About the App",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Version: 1.0.0",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Developed by Bo",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}