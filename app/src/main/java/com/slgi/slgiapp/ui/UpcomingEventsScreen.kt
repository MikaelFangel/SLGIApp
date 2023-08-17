package com.slgi.slgiapp.ui

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun UpcomingEventsScreen(viewModel: UpcomingEventsScreenViewModel) {
    val events = viewModel.events.collectAsState(initial = emptyList())

    Scaffold(
        bottomBar = {
            SLGINavBar(
                onNavigateToMyEvents = { /*TODO*/ },
                onNavigateToUpcomingEvents = { /*TODO*/ },
                onNavigateToProfile = { /*TODO*/ },
                onNavigateToUserRequests = { /*TODO*/ },
                admin = false,
                page = 1
            )
        }
    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
            items(items = events.value, key = { it.id }) {
                val participants =
                    viewModel.getParticipantFlow(it.id)?.collectAsState(initial = emptyList())
                EventCard(
                    eventImage = null,
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
                    eventNumberOfParticipants = it.participants.toString(),
                    eventFireleader = it.fireleader,
                    buttonText = if (participants != null && participants.value.isEmpty())
                        stringResource(R.string.participate) else stringResource(R.string.resign)
                ) {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.toggleParticipation(it.id)
                    }
                }
            }
        }
    }
}