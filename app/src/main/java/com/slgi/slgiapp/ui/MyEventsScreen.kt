package com.slgi.slgiapp.ui

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.slgi.slgiapp.R
import com.slgi.slgiapp.ui.shared.CreateEventModal
import com.slgi.slgiapp.ui.shared.EventCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun MyEventsScreen(
    viewModel: UpcomingEventsScreenViewModel,
    loginScreenViewModel: LoginScreenViewModel,
    bottomBar: @Composable () -> Unit,
) {
    val events = viewModel.events.collectAsState(initial = emptyList())
    val uiState = viewModel.uiState.collectAsState()
    val loginState = loginScreenViewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            if (loginState.value.isAdmin) {
                FloatingActionButton(
                    onClick = { viewModel.showCreateDialog() },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, "")
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
            items(items = events.value, key = { it.id }) {
                val userParticipation =
                    viewModel.getParticipantFlow(it.id)?.collectAsState(initial = emptyList())
                val participants =
                    viewModel.getParticipants(it.id).collectAsState(initial = emptyList())
                if(!(userParticipation != null && userParticipation.value.isEmpty())) {
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
                        buttonText = stringResource(R.string.resign),
                        participating = (userParticipation != null && userParticipation.value.isEmpty())
                    ) {
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.toggleParticipation(it.id)
                        }
                    }
                }
            }
        }

        if (uiState.value.displayCreateDialog) {
            CreateEventModal(viewModel = viewModel)
        }
    }
}