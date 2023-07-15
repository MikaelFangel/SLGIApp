package com.slgi.slgiapp.ui.upcomingeventsscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slgi.slgiapp.ui.shared.SLGINavBar

@Composable
fun UpcomingEventsScreen() {
    Scaffold(
        bottomBar = {
            SLGINavBar(
                onNavigateToMyEvents = { /*TODO*/ },
                onNavigateToUpcomingEvents = { /*TODO*/ },
                onNavigateToProfile = { /*TODO*/ },
                onNavigateToUserRequests = { /*TODO*/ },
                admin = false
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Hello World")
        }
    }
}