package com.example.boshowcase.ui.navigation

import androidx.annotation.DrawableRes
import com.example.boshowcase.R

/**
 * Bottom Navigation Items.
 *
 * @param route route string for navigation
 * @param title Title showing on the bottom nav
 * @param icon Icon showing on the bottom nav
 */
data class BottomNavItem(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
)

val bottomNavItems = listOf(
    BottomNavItem("profile", "Profile", R.drawable.ic_profile),
    BottomNavItem("portfolio", "Portfolio", R.drawable.ic_portfolio),
    BottomNavItem("resume", "Resume", R.drawable.ic_resume),
    BottomNavItem("settings", "Settings", R.drawable.ic_settings)
)