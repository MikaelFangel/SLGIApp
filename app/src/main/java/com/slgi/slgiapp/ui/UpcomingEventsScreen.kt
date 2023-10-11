package com.slgi.slgiapp.ui

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.slgi.slgiapp.R
import com.slgi.slgiapp.ui.shared.EventCard
import com.slgi.slgiapp.ui.shared.SLGINavBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun UpcomingEventsScreen(
    viewModel: UpcomingEventsScreenViewModel,
    loginScreenViewModel: LoginScreenViewModel
) {
    val events = viewModel.events.collectAsState(initial = emptyList())
    val uiState = viewModel.uiState.collectAsState()
    val loginState = loginScreenViewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            SLGINavBar(
                onNavigateToMyEvents = { /*TODO*/ },
                onNavigateToUpcomingEvents = { /*TODO*/ },
                onNavigateToProfile = { /*TODO*/ },
                onNavigateToUserRequests = { /*TODO*/ },
                admin = loginState.value.isAdmin,
                page = 1
            )
        },
        floatingActionButton = {
            if (loginState.value.isAdmin)
                IconButton(
                    onClick = {
                        viewModel.showCreateDialog()
                    },
                    colors = IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
                        disabledContentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = "none")
                }
        }
    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
            items(items = events.value, key = { it.id }) {
                val userParticipation =
                    viewModel.getParticipantFlow(it.id)?.collectAsState(initial = emptyList())
                val participants =
                    viewModel.getParticipants(it.id).collectAsState(initial = emptyList())
                EventCard(
                    eventImageURL = it.imageUrl,
                    eventName = it.name,
                    eventDescription = it.description,
                    eventDate = (SimpleDateFormat(
                        "dd-MM-yyyy",
                        Locale.getDefault()
                    ).format(it.dateAndTime.toDate())).toString(),
                    eventTime = (SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                    ).format(it.dateAndTime.toDate().time)).toString(),
                    eventNumberOfParticipants = participants.value.size.toString(),
                    eventFireleader = it.fireleader,
                    buttonText = if (userParticipation != null && userParticipation.value.isEmpty())
                        stringResource(R.string.participate) else stringResource(R.string.resign),
                    participating = (userParticipation != null && userParticipation.value.isEmpty())
                ) {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.toggleParticipation(it.id)
                    }
                }
            }
        }

        if (uiState.value.displayCreateDialog) {
            CreateEventModal(viewModel = viewModel)
        }
    }
}