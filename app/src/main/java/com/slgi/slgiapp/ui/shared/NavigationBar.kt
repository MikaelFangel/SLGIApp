package com.slgi.slgiapp.ui.shared

import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.slgi.slgiapp.ui.theme.SLGIAppTheme

@Composable
fun NavigationBarUser(onNavigateToMyEvents: () -> Unit,
                      onNavigateToUpcomingEvents: () -> Unit,
                      onNavigateToProfile: () -> Unit,
                      onNavigateToUserRequests: () -> Unit,
                      admin: Boolean
){
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ){
        NavigationBarItem(
            selected = false,
            icon = {
                Icon(
                    Icons.Outlined.EventAvailable,
                    contentDescription = null
                )},
            label = {
                    Text(text = "Mine")
            },
            onClick = onNavigateToMyEvents)
        NavigationBarItem(
            selected = false,
            icon = {
                Icon(
                    Icons.Outlined.CalendarViewDay,
                    contentDescription = null
                )},
            label = {
                Text(text = "Kommende")
            },
            onClick = onNavigateToUpcomingEvents)
        if (admin){
            NavigationBarItem(
                selected = false,
                icon = {
                    Icon(
                        Icons.Outlined.PersonAdd,
                        contentDescription = null
                    )},
                label = {
                    Text(text = "Anmodninger")
                },
                onClick = onNavigateToUserRequests)
        }
        NavigationBarItem(
            selected = false,
            icon = {
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = null
                )},
            label = {
                Text(text = "Profil")
            },
            onClick = onNavigateToProfile)
    }
}



@Preview (showBackground = true)
@Composable
fun NavigationBarPreview(){
    SLGIAppTheme(dynamicColor = false){
        Surface() {
            NavigationBarUser({},{},{},{},true)
        }
    }
}