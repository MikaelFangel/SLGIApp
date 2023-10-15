package com.slgi.slgiapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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


    // TODO Temporary delete user safety
    val showSafetyDialog = remember {
        mutableStateOf(false)
    }
    if (showSafetyDialog.value) {
        safetyDialog(showSafetyDialog) {
            loginScreenViewModel.deleteUser()
            showSafetyDialog.value = false
            navController.navigate(Screens.LOGIN_SCREEN.name) {
                popUpTo(0)
            }
        }
    }

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
                    R.string.deleteUser to Pair(Icons.Outlined.DeleteForever) {
                        showSafetyDialog.value = true
                    },
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
        composable(Screens.MYEVENTS_SCREEN.name) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun safetyDialog(show: MutableState<Boolean>, onConfirm: () -> Unit) {
    AlertDialog(onDismissRequest = { show.value = false }) {
        ElevatedCard {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = stringResource(id = R.string.areYouSure))
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { show.value = false }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Button(
                        onClick = { onConfirm() },
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            disabledContentColor = MaterialTheme.colorScheme.onTertiary
                        )
                    ) {
                        Text(text = stringResource(id = R.string.approve))
                    }
                }
            }
        }
    }
}