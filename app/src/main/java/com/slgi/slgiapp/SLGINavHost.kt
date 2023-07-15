package com.slgi.slgiapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.slgi.slgiapp.ui.upcomingeventsscreen.UpcomingEventsScreen

enum class Screens {
    UPCOMING_SCREEN,
}

@Composable
fun SLGINavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.UPCOMING_SCREEN.name
    ) {
        composable(Screens.UPCOMING_SCREEN.name) {
            UpcomingEventsScreen()
        }
    }
}