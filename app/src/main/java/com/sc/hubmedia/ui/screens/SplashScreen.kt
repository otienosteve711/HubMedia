package com.sc.hubmedia.ui.screens

import android.window.SplashScreen
import android.window.SplashScreenView
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController //navigation
// animation effects
//import androidx.compose.animation.core
// allows us to select a background effect for this screen
import androidx.compose.foundation.background
// layout configuration
import androidx.compose.foundation.layout.*
// material design imports
import androidx.compose.material3.*
// all compose runtime features
import androidx.compose.runtime.*
// Ui alignments , drawing and scaling(i.e. measurements)
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
// navigation
import androidx.navigation.compose.rememberNavController
// screens
import kotlinx.coroutines.delay
// material design icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VideoLibrary
import com.sc.hubmedia.navigation.Screen
import com.sc.hubmedia.ui.theme.MediaHubTheme

// timer delay
// import kotlin.coroutines.delay
@Composable
fun SplashScreen(navController: NavController){
    // animating my logo scale
    val scale = remember { Animatable(initialValue = 0f) }
    // launched effect to delay splash screen showcase
    // basically we want the screen to be in view for a number of seconds
    LaunchedEffect(key1 = Unit){
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        delay(timeMillis = 1500) // splash screen to last 1.5 seconds
        // after the 1.5 seconds we then redirect the user to the login screen
        navController.navigate(Screen.Login.route){
            // splash will become the backstack screen i.e. when user presses back from login
            popUpTo(Screen.Splash.route){
                inclusive=true
            }
        }
    }
    // define our logo
    // Box : another example of a container for hosting composable elements
    Box(Modifier.fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        // column for vertical arrangement
        Column(horizontalAlignment = Alignment.CenterHorizontally)
        {
            // Icon and Text together
            Icon(
                imageVector = Icons.Default.VideoLibrary,
                contentDescription = "HubMedia Logo",
                tint=MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(96.dp)
                    .scale(scale.value)
            )
            Spacer(Modifier.height(16.dp))
            Text(text="HubMedia",
                style=MaterialTheme.typography.bodyLarge,
                color=MaterialTheme.colorScheme.onSurface
                    .copy(alpha = 0.6f))
            Text(text="HubMedia",
                style=MaterialTheme.typography.bodyLarge,
                color=MaterialTheme.colorScheme.onSurface
                    .copy(alpha = 0.6f))

        }
    }

}
@Preview(showBackground = true)
@Composable
fun SplashScreenView(){
    MediaHubTheme{
        SplashScreen(navController = rememberNavController())
    }
}