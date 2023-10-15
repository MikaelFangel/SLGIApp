package com.slgi.slgiapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.slgi.slgiapp.data.EventNetworkDataSource
import com.slgi.slgiapp.data.EventRepository
import com.slgi.slgiapp.data.LoginNetworkDataSource
import com.slgi.slgiapp.data.LoginRepository
import com.slgi.slgiapp.data.RequestDataSource
import com.slgi.slgiapp.data.RequestRepository
import com.slgi.slgiapp.ui.LoginScreen
import com.slgi.slgiapp.ui.LoginScreenViewModel
import com.slgi.slgiapp.ui.MyEventsScreen
import com.slgi.slgiapp.ui.ProfileScreen
import com.slgi.slgiapp.ui.RegistrationScreen
import com.slgi.slgiapp.ui.RegistrationScreenViewModel
import com.slgi.slgiapp.ui.UpcomingEventsScreen
import com.slgi.slgiapp.ui.UpcomingEventsScreenViewModel
import com.slgi.slgiapp.ui.UserRequestsScreen
import com.slgi.slgiapp.ui.UserRequestsViewModel
import com.slgi.slgiapp.ui.shared.SLGINavBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SLGINavHost(
    navController: NavHostController = rememberNavController(),
) {
    val loginScreenViewModel = remember {
        LoginScreenViewModel(LoginRepository(LoginNetworkDataSource()))
    }
    val upcomingEventsScreenViewModel = remember {
        UpcomingEventsScreenViewModel(
            EventRepository(
                EventNetworkDataSource()
            )
        )
    }
    val userRequestsViewModel = remember {
        UserRequestsViewModel(
            RequestRepository(RequestDataSource()),
            LoginRepository(LoginNetworkDataSource())
        )
    }
    val registrationScreenViewModel = remember {
        RegistrationScreenViewModel(RequestRepository(RequestDataSource()))
    }

    val loginState = loginScreenViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screens.LOGIN_SCREEN.name
    ) {
        composable(Screens.UPCOMING_SCREEN.name) {
            UpcomingEventsScreen(
                viewModel = upcomingEventsScreenViewModel,
                loginScreenViewModel = loginScreenViewModel,
                bottomBar = {
                    SLGINavBar(
                        onNavigateToMyEvents = { navController.navigate(Screens.MYEVENTS_SCREEN.name) },
                        onNavigateToUpcomingEvents = { /* Left blank intentionally */ },
                        onNavigateToProfile = { navController.navigate(Screens.PROFILE_SCREEN.name) },
                        onNavigateToUserRequests = { navController.navigate(Screens.REQUEST_SCREEN.name) },
                        admin = loginState.value.isAdmin,
                        page = Screens.UPCOMING_SCREEN.ordinal
                    )
                },
            )
        }
        composable(Screens.LOGIN_SCREEN.name) {
            LoginScreen(
                viewModel = loginScreenViewModel,
                loginAction = {
                    navController.navigate(Screens.UPCOMING_SCREEN.name) {
                        popUpTo(0)
                    }
                },
                requestAction = {
                    navController.navigate(Screens.REGISTRATION_SCREEN.name) {

                    }
                },
                forgotPasswordAction = {
                    CoroutineScope(Dispatchers.IO).launch {
                        loginScreenViewModel.resetPassword(loginState.value.email)
                    }
                }
            )
        }
        composable(Screens.REGISTRATION_SCREEN.name) {
            RegistrationScreen(registrationScreenViewModel, { navController.popBackStack() }) {
                navController.navigate(Screens.LOGIN_SCREEN.name) {
                    popUpTo(0)
                }
            }
        }
        composable(Screens.REQUEST_SCREEN.name) {
            UserRequestsScreen(
                viewModel = userRequestsViewModel,
                bottomBar = {
                    SLGINavBar(
                        onNavigateToMyEvents = { navController.navigate(Screens.MYEVENTS_SCREEN.name) },
                        onNavigateToUpcomingEvents = { navController.navigate(Screens.UPCOMING_SCREEN.name) },
                        onNavigateToProfile = { navController.navigate(Screens.PROFILE_SCREEN.name) },
                        onNavigateToUserRequests = { /* Left blank intentionally */ },
                        admin = loginState.value.isAdmin,
                        page = Screens.REQUEST_SCREEN.ordinal
                    )
                }
            )
        }
        composable(Screens.PROFILE_SCREEN.name) {
            ProfileScreen(
                bottomBar = {
                    SLGINavBar(
                        onNavigateToMyEvents = { navController.navigate(Screens.MYEVENTS_SCREEN.name) },
                        onNavigateToUpcomingEvents = { navController.navigate(Screens.UPCOMING_SCREEN.name) },
                        onNavigateToProfile = { /* Left blank intentionally */ },
                        onNavigateToUserRequests = { navController.navigate(Screens.REQUEST_SCREEN.name) },
                        page = Screens.PROFILE_SCREEN.ordinal,
                        admin = loginState.value.isAdmin
                    )
                },
                // The map contains the prefix icon, the description, and the action to be performed onclick
                navigationMap = mapOf(
                    R.string.logout to Pair(Icons.AutoMirrored.Outlined.Logout) {
                        loginScreenViewModel.logout()
                        navController.navigate(Screens.LOGIN_SCREEN.name) {
                            popUpTo(0)
                        }
                    }
                ),
                goBackAction = { navController.popBackStack() }
            )
        }
        composable(Screens.MYEVENTS_SCREEN.name){
            MyEventsScreen(
                viewModel = upcomingEventsScreenViewModel,
                bottomBar = {
                    SLGINavBar(
                        onNavigateToMyEvents = { /* Left blank intentionally */ },
                        onNavigateToUpcomingEvents = { navController.navigate(Screens.UPCOMING_SCREEN.name) },
                        onNavigateToProfile = { navController.navigate(Screens.PROFILE_SCREEN.name) },
                        onNavigateToUserRequests = { navController.navigate(Screens.REQUEST_SCREEN.name) },
                        admin = loginState.value.isAdmin,
                        page = Screens.MYEVENTS_SCREEN.ordinal
                    )
                },
            )
        }
    }
}