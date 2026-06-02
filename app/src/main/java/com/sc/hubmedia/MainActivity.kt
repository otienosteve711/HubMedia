package com.sc.hubmedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.sc.hubmedia.navigation.MediaHubNavGraph
import com.sc.hubmedia.ui.theme.MediaHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MediaHubTheme {
                // stipulate app to use the navgraph
                // for screen load and start destination screens
                val navController = rememberNavController()
                MediaHubNavGraph(navController)
                }
            }
        }
    }


