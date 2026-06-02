package com.sc.hubmedia.navigation
// this defines a navigation paths to our screens definitions inside screens.kt
// allows us to write compose functions // render composable elements
import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.navigation.NavController
// this is the navigation manager that allows movement from one screen to another through path definition
import androidx.navigation.NavHostController
// allows us to define navigation type i.e.
// backstack: previous screen // foreground : screen in view
import androidx.navigation.NavType
// container for all our navigation screens
import androidx.navigation.compose.NavHost
// allows definition of  navigation composable functions
import androidx.navigation.compose.composable
// carries path route name to different screens // navigation
import androidx.navigation.navArgument
// importing all our screens
import com.sc.hubmedia.ui.screens.*

@Composable
fun MediaHubNavGraph(navController: NavHostController){
    // we define our navigation container
    // stipulate the default start destination (where does the app start)
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ){
        // state our app screens that are def. in screen.kt
        composable(Screen.Splash.route){
            SplashScreen(navController)
        }
        composable(Screen.Login.route){
            LoginScreen(navController)
        }
        composable(Screen.Register.route){
            RegisterScreen(navController)
        }
        composable(Screen.ForgotPassword.route){
            ForgotPasswordScreen(navController)
        }
        composable(Screen.Dashboard.route){
            DashboardScreen(navController)
        }


        composable(Screen.UploadMedia.route){
            UploadMediaScreen(navController)
        }
        // for screens which require info on navigation we use the arguments attribute together with
        // navtype to define datatype of info shared to access route
        composable(route = Screen.MediaDetail.route,
            arguments = listOf(navArgument(name="mediaId")
            {type= NavType.StringType})){backStack ->
            // inside we maintain a backstack i.e. when user presses back we go back to
            // the previous screen without the ID
            val mediaId = backStack.arguments?.getString("mediaId")?: ""
            MediaDetailScreen(navController, mediaId)

        }
        composable(route = Screen.EditMedia.route,
            arguments = listOf(navArgument(name="mediaId")
            {type= NavType.StringType})){ backStack ->
            val mediaId = backStack.arguments?.getString("mediaId")?: ""
            EditMediaScreen(navController, mediaId)

        }

    }
}

