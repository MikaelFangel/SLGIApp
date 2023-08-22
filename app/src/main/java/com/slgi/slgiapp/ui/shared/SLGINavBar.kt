package com.slgi.slgiapp.ui.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarViewDay
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.slgi.slgiapp.R
import com.slgi.slgiapp.ui.theme.SLGIAppTheme

@Composable
fun SLGINavBar(
    onNavigateToMyEvents: () -> Unit,
    onNavigateToUpcomingEvents: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToUserRequests: () -> Unit,
    page: Int,
    admin: Boolean
) {
    val pageSelected = arrayOf(false, false, false, false)
    pageSelected[page] = true
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        NavigationBarItem(
            selected = pageSelected[0],
            icon = {
                Icon(
                    Icons.Outlined.EventAvailable,
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(id = R.string.nav_label_my_events))
            },
            onClick = onNavigateToMyEvents
        )
        NavigationBarItem(
            selected = pageSelected[1],
            icon = {
                Icon(
                    Icons.Outlined.CalendarViewDay,
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(id = R.string.nav_label_upcoming_events))
            },
            onClick = onNavigateToUpcomingEvents
        )
        if (admin) {
            NavigationBarItem(
                selected = pageSelected[2],
                icon = {
                    Icon(
                        Icons.Outlined.PersonAdd,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.nav_label_requests))
                },
                onClick = onNavigateToUserRequests
            )
        }
        NavigationBarItem(
            selected = pageSelected[3],
            icon = {
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(id = R.string.nav_label_profile))
            },
            onClick = onNavigateToProfile
        )
    }
}


@Preview(showBackground = true)
@Composable
fun NavigationBarPreview() {
    SLGIAppTheme(dynamicColor = false) {
        Surface() {
            SLGINavBar({}, {}, {}, {}, 3, true)
        }
    }
}