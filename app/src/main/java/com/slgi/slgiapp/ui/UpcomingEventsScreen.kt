package com.slgi.slgiapp.ui.upcomingeventsscreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.slgi.slgiapp.data.EventNetworkDataSource
import com.slgi.slgiapp.data.EventRepository
import com.slgi.slgiapp.ui.UpcomingEventsScreenViewModel
import com.slgi.slgiapp.ui.shared.EventCard
import com.slgi.slgiapp.ui.shared.SLGINavBar
import kotlinx.coroutines.flow.toList

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
                EventCard(
                    eventImage = null,
                    eventName = it.name,
                    eventDescription = it.description,
                    eventDate = it.date,
                    eventTime = it.time,
                    eventNumberOfParticipants = it.participants.toString(),
                    eventFireleader = it.fireLeader,
                    buttonText = "Participate"
                ) {

                }
            }
        }
    }
}