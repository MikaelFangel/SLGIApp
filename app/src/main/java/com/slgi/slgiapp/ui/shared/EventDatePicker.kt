package com.slgi.slgiapp.ui.shared

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.slgi.slgiapp.R
import com.slgi.slgiapp.ui.UpcomingEventsScreenViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDateDialog(viewModel: UpcomingEventsScreenViewModel){
    val datePickerState = rememberDatePickerState(
        selectableDates = DateSelector()
    )

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
                enabled = datePickerState.selectedDateMillis != null
            ) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    viewModel.dismissDateDialog()
                }
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }

}
@OptIn(ExperimentalMaterial3Api::class)
class DateSelector : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean{
        val today = LocalDate.now().atStartOfDay()
        val checkedDay = LocalDateTime.ofInstant(Instant.ofEpochSecond(utcTimeMillis.div(1000)),ZoneOffset.UTC)
        return today.isEqual(checkedDay) || today.isBefore(checkedDay)
    }
}