package com.example.boshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.boshowcase.data.preference.PreferencesManager
import com.example.boshowcase.ui.mainscreen.MainScreen
import com.example.boshowcase.ui.settingsscreen.SettingsViewModel
import com.example.boshowcase.ui.splashscreen.SplashScreen
import com.example.boshowcase.ui.theme.BoShowcaseTheme

/**
 * Main Activity. This app will contain single activity.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val preferencesManager = PreferencesManager(this)
            val viewModel = SettingsViewModel(preferencesManager)
            BoShowcaseApp(viewModel)
        }
    }
}

@Composable
fun BoShowcaseApp(viewModel: SettingsViewModel) {
    val navController = rememberNavController()
    val isDarkModeEnabled by viewModel.isDarkTheme.collectAsState(initial = false)
    BoShowcaseTheme(darkTheme = isDarkModeEnabled) {
        AppNavigation(navController)
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("main") { MainScreen() }
    }
}