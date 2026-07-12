// for nav items below the screen
package com.example.nextask.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : NavItem("home", "Home", Icons.Default.Home)
    object Tasks : NavItem("tasks", "Tasks", Icons.Default.AssignmentTurnedIn)
    object Analytics : NavItem("analytics", "Analytics", Icons.Default.Analytics)
    object Profile : NavItem("profile", "Profile", Icons.Default.Person)
}