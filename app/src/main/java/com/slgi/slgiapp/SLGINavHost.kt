package com.slgi.slgiapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.slgi.slgiapp.data.EventNetworkDataSource
import com.slgi.slgiapp.data.EventRepository
import com.slgi.slgiapp.ui.LoginScreen
import com.slgi.slgiapp.ui.LoginScreenViewModel
import com.slgi.slgiapp.ui.UpcomingEventsScreen
import com.slgi.slgiapp.ui.UpcomingEventsScreenViewModel

enum class Screens {
    LOGIN_SCREEN,
    UPCOMING_SCREEN,
}

@Composable
fun SLGINavHost(
    navController: NavHostController = rememberNavController(),
) {
    val loginScreenViewModel = remember {
        LoginScreenViewModel()
    }
    val upcomingEventsScreenViewModel = remember {
        UpcomingEventsScreenViewModel(
            EventRepository(
                EventNetworkDataSource()
            )
        )
    }

    NavHost(
        navController = navController,
        startDestination = Screens.LOGIN_SCREEN.name
    ) {
        composable(Screens.UPCOMING_SCREEN.name) {
            UpcomingEventsScreen(viewModel = upcomingEventsScreenViewModel)
        }
        composable(Screens.LOGIN_SCREEN.name) {
            LoginScreen(
                viewModel = loginScreenViewModel,
                loginAction = { /*TODO*/ },
                requestAction = { /*TODO*/ },
                forgotPasswordAction = {
                    /* TODO */
                }
            )
        }
    }
}