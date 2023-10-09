package com.slgi.slgiapp.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.slgi.slgiapp.ui.UpcomingEventsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTimeDialog(viewModel: UpcomingEventsScreenViewModel) {


    val timePickerState = rememberTimePickerState(
        initialHour = 10,
        initialMinute = 0,
        is24Hour = true
    )

    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(size = 20.dp)
            )
            ,
        onDismissRequest = { viewModel.dismissTimeDialog() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp, 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // time picker
            TimePicker(
                state = timePickerState,
                colors = TimePickerColors(
                    clockDialColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectorColor = MaterialTheme.colorScheme.primary,
                    clockDialSelectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    clockDialUnselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    periodSelectorBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    periodSelectorUnselectedContainerColor = MaterialTheme.colorScheme.onError,
                    periodSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    periodSelectorUnselectedContentColor = MaterialTheme.colorScheme.error,
                    timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )

            // buttons
            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // dismiss button
                TextButton(onClick = {
                    viewModel.dismissTimeDialog()
                }) {
                    Text(text = "Dismiss")
                }

                // confirm button
                TextButton(
                    onClick = {
                        viewModel.setNewEventTime(timePickerState.hour, timePickerState.minute)
                        viewModel.dismissTimeDialog()
                    }
                ) {
                    Text(text = "Confirm")
                }
            }
        }
    }

}