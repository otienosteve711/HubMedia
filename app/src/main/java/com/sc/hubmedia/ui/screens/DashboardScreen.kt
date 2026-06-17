package com.sc.hubmedia.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sc.hubmedia.model.UserRole
import com.sc.hubmedia.navigation.Screen
import com.sc.hubmedia.ui.components.AppDrawer
import com.sc.hubmedia.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun DashboardScreen(navController: NavController, authViewModel: AuthViewModel= viewModel()
){
    val authState by authViewModel.authState.collectAsState()
    val profile by authViewModel.currentProfile.collectAsState()
    val isTeacher = profile?.userRole() == UserRole.TEACHER
    // initial state of our drawer
    val drawerScope = rememberDrawerState(initialValue = DrawerValue.Closed)
    // to make updates to our drawerscope
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerScope,
        drawerContent = {
            AppDrawer(
                profile = profile,
                "dashboard",
                onDashboardClick = {scope.launch { drawerScope.close() }},
                onUploadClick = {scope.launch { drawerScope.close() }
                navController.navigate(Screen.UploadMedia.route)},
                onProfileClick = {},
                onLogoutClick = {
                    scope.launch { drawerScope.close() }
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route){popUpTo(0){inclusive=true}
                    }
                }
            ) {
                //Scaffold : allows definition of different parts of  the ui
                Scaffold(
                    topBar = {},
                    bottomBar = {},
                    containerColor = MaterialTheme.colorScheme.background
                ){}
            }
        }
    ) { }

    }

