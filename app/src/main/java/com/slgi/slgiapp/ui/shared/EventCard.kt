package com.slgi.slgiapp.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.InsertInvitation
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slgi.slgiapp.R
import com.slgi.slgiapp.ui.theme.SLGIAppTheme

@Composable
fun EventCard(
    eventImage: ImageBitmap,
    eventName: String,
    eventDescription: String,
    eventDate: String,
    eventTime: String,
    eventNumberOfParticipants: String,
    eventFireleader: String,
    buttonText: String,
    buttonOnClickAction: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.padding(5.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)) {
            Image(
                eventImage,
                modifier = Modifier.clip(shape = CardDefaults.elevatedShape),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
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
            Divider()
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
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
            Divider()
            Spacer(modifier = Modifier.height(5.dp))

            // Fireleader information and button for participation
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    EventInformation(Icons.Outlined.Person, null, eventFireleader)
                }
                ParticipationButton(buttonText, buttonOnClickAction)
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
fun ParticipationButton(buttonText: String, onClickAction: () -> Unit) {
    Button(
        onClick = { onClickAction },
        colors = ButtonDefaults.buttonColors(
            MaterialTheme.colorScheme.primaryContainer
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
                eventImage = ImageBitmap.imageResource(id = R.drawable.pexels_maur_cio_mascaro_1592109),
                eventName = "Event Name",
                eventDescription = "Event Description",
                eventDate = "1. maj 2023",
                eventTime = "10:00-13:00",
                eventNumberOfParticipants = "10",
                eventFireleader = "A. Andersen",
                buttonText = "Button Text"
            ) {
            }
        }
    }
}