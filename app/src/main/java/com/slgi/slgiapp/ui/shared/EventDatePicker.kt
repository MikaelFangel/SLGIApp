package com.slgi.slgiapp.ui.shared

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import com.slgi.slgiapp.ui.UpcomingEventsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDateDialog(viewModel: UpcomingEventsScreenViewModel){
    val datePickerState = rememberDatePickerState()
    var confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
    DatePickerDialog(
        onDismissRequest = {
            viewModel.dismissDateDialog()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.dismissDateDialog()
                    datePickerState.selectedDateMillis?.let { viewModel.setNewEventDate(it) }

                },
                enabled = confirmEnabled.value
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    viewModel.dismissDateDialog()
                }
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }

}