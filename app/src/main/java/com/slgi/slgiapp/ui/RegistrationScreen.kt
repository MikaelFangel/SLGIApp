package com.slgi.slgiapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.slgi.slgiapp.R
import com.slgi.slgiapp.ui.shared.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    viewModel: RegistrationScreenViewModel,
    onRequest: () -> Unit,
    goBackAction: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBar(
            barTitle = stringResource(id = R.string.requestAccessLabel),
            goBackAction = goBackAction) },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        val focusManager = LocalFocusManager.current

        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.firstNameLabel)) },
                singleLine = true,
                value = uiState.value.firstname,
                onValueChange = { viewModel.onFirstNameChange(it.filter { c -> nameFilter(c) }) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.lastNameLabel)) },
                singleLine = true,
                value = uiState.value.lastname,
                onValueChange = { viewModel.onLastNameChange(it.filter { c -> nameFilter(c) }) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.emailLabel)) },
                singleLine = true,
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
                singleLine = true,
                value = uiState.value.password, onValueChange = { viewModel.onPasswordChange(it) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Outlined.Visibility
                    else Icons.Outlined.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
            )
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.repeatPasswordLabel)) },
                singleLine = true,
                value = uiState.value.passwordRep,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = { viewModel.onPasswordRepChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Outlined.Visibility
                    else Icons.Outlined.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = uiState.value.terms,
                    onCheckedChange = { viewModel.onTermsChange() })
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = stringResource(id = R.string.acceptsTerms))
            }
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            viewModel.requestAccess()
                            onRequest()
                        } catch (e: Exception) {
                            scope.launch { e.message?.let { snackbarHostState.showSnackbar(message = it) } }
                        }
                    }
                }) {
                Text(text = stringResource(id = R.string.requestAccessLabel))
            }
        }
    }
}

fun nameFilter(c: Char) = c.isLetter() || c == ' ' || c == '-'
