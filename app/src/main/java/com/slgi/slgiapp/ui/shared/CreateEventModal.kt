package com.slgi.slgiapp.ui.shared

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.slgi.slgiapp.R
import com.slgi.slgiapp.ui.UpcomingEventsScreenViewModel
import java.util.Locale

@Composable
fun CreateEventModal(viewModel: UpcomingEventsScreenViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    var timeDisplay = "hh:mm"
    if (uiState.value.newEventHours != null && uiState.value.newEventMinutes != null) {
        timeDisplay =
            uiState.value.newEventHours.toString() + ":" + uiState.value.newEventMinutes.toString()
    }
    Dialog(
        onDismissRequest = { viewModel.dismissCreateDialog() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        ElevatedCard(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier.padding(30.dp, 5.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.createEventTitle),
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = { viewModel.dismissCreateDialog() },
                        modifier = Modifier.scale(0.7f)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Cancel,
                            contentDescription = stringResource(id = R.string.cancel)
                        )
                    }
                }

                HorizontalDivider()
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    label = { Text(text = stringResource(id = R.string.eventName)) },
                    value = uiState.value.newEventName,
                    enabled = true,
                    maxLines = 1,
                    onValueChange = { viewModel.onEventNameChange(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    label = { Text(text = stringResource(id = R.string.description)) },
                    value = uiState.value.newEventDescription,
                    enabled = true,
                    maxLines = 4,
                    onValueChange = { viewModel.onEventDescChange(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    label = { Text(text = stringResource(id = R.string.time)) },
                    value = timeDisplay,
                    placeholder = { Text(text = "hh:mm") },
                    maxLines = 1,
                    onValueChange = { /*TODO*/ },
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = { viewModel.showTimeDialog() }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Timer,
                                contentDescription = stringResource(id = R.string.chooseTime)
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    label = { Text(text = stringResource(id = R.string.date)) },
                    value = SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    ).format(uiState.value.newEventDate.toDate()).toString(),
                    placeholder = { Text(text = "dd/mm/yyyy") },
                    maxLines = 1,
                    onValueChange = { /*TODO*/ },
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = { viewModel.showDateDialog() }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CalendarMonth,
                                contentDescription = stringResource(id = R.string.chooseDate)
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    label = { Text(text = stringResource(id = R.string.imageUrl)) },
                    value = uiState.value.newEventImageURL,
                    maxLines = 1,
                    enabled = true,
                    onValueChange = { viewModel.onEventImgUrlChange(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Image,
                            contentDescription = stringResource(id = R.string.chooseImage)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    label = { Text(text = stringResource(id = R.string.Fireleader)) },
                    value = uiState.value.newEventFireLeader,
                    enabled = true,
                    maxLines = 4,
                    onValueChange = { viewModel.onFireLeaderChange(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(50.dp, 5.dp)
                    .fillMaxWidth()
            ) {
                TextButton(
                    onClick = { viewModel.dismissCreateDialog() },
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                TextButton(
                    onClick = {
                        viewModel.createEvent()
                        viewModel.dismissCreateDialog()
                    },
                    modifier = Modifier.width(100.dp),
                    enabled = !(uiState.value.newEventName.length <= 1 && uiState.value.newEventHours == null && uiState.value.newEventMinutes == null)
                ) {
                    Text(
                        text = stringResource(id = R.string.create),
                        //color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        if (uiState.value.displayDateDialog) {
            SelectDateDialog(viewModel = viewModel)
        }
        if (uiState.value.displayTimeDialog) {
            SelectTimeDialog(
                initialTime = uiState.value.newEventTime,
                dismiss = { viewModel.dismissTimeDialog() },
                setTime = { viewModel.setNewEventTime(it) }
                )
        }
    }
}

