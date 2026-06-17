package com.sc.hubmedia.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sc.hubmedia.ui.theme.MediaHubTheme

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onUploadClick: () -> Unit,
    onDashboardClick: () -> Unit,
    onProfileClick: () -> Unit
){

  // host - navigation bar
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.onSurface
    ) {
        NavigationBarItem(
            selected = currentRoute == "dashboard",
            onClick = onDashboardClick,
            icon = {Icon(Icons.Default.Dashboard, "Dashboard")},
            label = {Text("Dashboard")}
        )

        NavigationBarItem(
            selected = currentRoute == "upload_media",
            onClick = onUploadClick,
            icon = {Icon(Icons.Default.CloudUpload, "Upload")},
            label = {Text("Upload Media")}
        )

        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = onProfileClick,
            icon = {Icon(Icons.Default.Person, "Profile")},
            label = {Text("Profile")}
        )
    }
}