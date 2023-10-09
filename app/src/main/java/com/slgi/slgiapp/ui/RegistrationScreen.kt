package com.slgi.slgiapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.slgi.slgiapp.R
import com.slgi.slgiapp.ui.shared.TopBar

@Composable
fun RegistrationScreen(viewModel: RegistrationScreenViewModel) {
    Scaffold(
        topBar = { TopBar(barTitle = stringResource(id = R.string.requestAccessLabel)) }
    ) { innerPadding ->
        val uiState = viewModel.uiState.collectAsState()
        val focusManager = LocalFocusManager.current

        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.firstNameLabel)) },
                value = uiState.value.firstname,
                onValueChange = { viewModel.onFirstNameChange(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.lastNameLabel)) },
                value = uiState.value.lastname, onValueChange = { viewModel.onLastNameChange(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.emailLabel)) },
                value = uiState.value.email, onValueChange = { viewModel.onEmailChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.passwordLable)) },
                value = uiState.value.password, onValueChange = { viewModel.onPasswordChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.repeatPasswordLabel)) },
                value = uiState.value.passwordRep,
                onValueChange = { viewModel.onPasswordRepChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = uiState.value.terms,
                    onCheckedChange = { viewModel.onTermsChange() })
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = stringResource(id = R.string.acceptsTerms))
            }
            Button(
                onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.requestAccessLabel))
            }
        }
    }
}