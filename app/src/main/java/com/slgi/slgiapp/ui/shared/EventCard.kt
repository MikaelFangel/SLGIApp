package com.slgi.slgiapp.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.InsertInvitation
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.slgi.slgiapp.ui.theme.SLGIAppTheme

@Composable
fun EventCard(
    eventImageURL: String?,
    eventName: String,
    eventDescription: String,
    eventDate: String,
    eventTime: String,
    eventNumberOfParticipants: String,
    eventFireleader: String,
    buttonText: String,
    participating: Boolean,
    isAdmin: Boolean,
    onDeleteAction: () -> Unit,
    buttonOnClickAction: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.padding(5.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
        ) {
            if (eventImageURL != null) {
                AsyncImage(
                    eventImageURL,
                    modifier = Modifier.clip(shape = CardDefaults.elevatedShape),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        .clip(shape = CardDefaults.elevatedShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Outlined.Image,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.inverseOnSurface
                    )
                }
            }
            Box(
                Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                if (isAdmin) {
                    IconButton(
                        onClick = onDeleteAction,
                        colors = IconButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer,
                            disabledContentColor = MaterialTheme.colorScheme.tertiaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    ) {
                        Icon(
                            Icons.Outlined.DeleteForever,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(30.dp, 5.dp)) {
            // Description of the event
            Text(
                text = eventName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = eventDescription,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(10.dp))

            // General information about the event
            HorizontalDivider()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 5.dp)
            ) {
                EventInformation(
                    icon = Icons.Outlined.InsertInvitation,
                    contentDescription = null,
                    information = eventDate
                )
                Spacer(modifier = Modifier.width(20.dp))
                EventInformation(
                    icon = Icons.Outlined.WatchLater,
                    contentDescription = null,
                    information = eventTime
                )
                Spacer(modifier = Modifier.width(20.dp))
                EventInformation(
                    icon = Icons.Outlined.Groups,
                    contentDescription = null,
                    information = eventNumberOfParticipants
                )
            }
            HorizontalDivider()
            Spacer(modifier = Modifier.height(5.dp))

            // Fireleader information and button for participation
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    if (eventFireleader != "") {
                        EventInformation(Icons.Outlined.Person, null, eventFireleader)
                    }
                }
                ParticipationButton(buttonText, participating, buttonOnClickAction)
            }
        }
    }
}

@Composable
fun EventInformation(icon: ImageVector, contentDescription: String?, information: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            icon,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = contentDescription
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = information,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun ParticipationButton(buttonText: String, participating: Boolean, onClickAction: () -> Unit) {
    Button(
        onClick = onClickAction,
        colors =
        ButtonDefaults.buttonColors(
            if (participating) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
        )
    ) {
        Text(
            text = buttonText,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EventCardPreview() {
    SLGIAppTheme(dynamicColor = false) {
        Surface {
            EventCard(
                eventImageURL = null,
                eventName = "Event Name",
                eventDescription = "Event Description",
                eventDate = "1. maj 2023",
                eventTime = "10:00-13:00",
                eventNumberOfParticipants = "10",
                eventFireleader = "A. Andersen",
                buttonText = "Button Text",
                participating = true,
                isAdmin = true,
                onDeleteAction = {},
            ) {
            }
        }
    }
}