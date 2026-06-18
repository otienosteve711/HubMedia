package com.sc.hubmedia.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sc.hubmedia.ui.theme.MediaHubTheme

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onUploadClick: () -> Unit,
    onDashboardClick: () -> Unit,
    onProfileClick: () -> Unit
){
    // define a reference for our bottom nav background color
    val barBlue = Color(0xFF1565C0)
    val red = Color.Red // picking color via property

  // host - navigation bar
    NavigationBar(
        containerColor = barBlue,// background color for bottom nav
        modifier = Modifier.height(64.dp) // height for bottom nav
    ) {
        // itemcolors = showcase for when items are clicked
        val itemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = Color.White,
            unselectedIconColor = Color.Gray,
            unselectedTextColor = Color.Gray,
            indicatorColor = Color.White
        )
        NavigationBarItem(
            colors = itemColors,
            selected = currentRoute == "dashboard",
            onClick = onDashboardClick,
            icon = {Icon(Icons.Default.Dashboard, "Dashboard")},
            label = {Text("Dashboard")}
        )

        NavigationBarItem(
            colors = itemColors,
            selected = currentRoute == "upload_media",
            onClick = onUploadClick,
            icon = {Icon(Icons.Default.CloudUpload, "Upload")},
            label = {Text("Upload Media")}
        )

        NavigationBarItem(
            colors = itemColors,
            selected = currentRoute == "profile",
            onClick = onProfileClick,
            icon = {Icon(Icons.Default.Person, "Profile")},
            label = {Text("Profile")}
        )
    }
}