package com.slgi.slgiapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.slgi.slgiapp.R
import com.slgi.slgiapp.data.LoginNetworkDataSource
import com.slgi.slgiapp.data.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    loginAction: () -> Unit,
    requestAction: () -> Unit,
    forgotPasswordAction: (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = stringResource(id = R.string.organization_name),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(15.dp))

            Image(
                ImageBitmap.imageResource(id = R.drawable.pexels_maur_cio_mascaro_1592109),
                modifier = Modifier
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(20.dp))

            var passwordVisible by rememberSaveable { mutableStateOf(false) }

            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.emailLabel)) },
                value = uiState.value.email,
                singleLine = true,
                onValueChange = { viewModel.onEmailChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.passwordLable)) },
                singleLine = true,
                value = uiState.value.password,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                onValueChange = { viewModel.onPasswordChange(it) },
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Outlined.Visibility
                    else Icons.Outlined.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                keyboardActions = KeyboardActions(onDone = {
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            viewModel.login()
                            loginAction()
                        } catch (e: Exception) {
                            scope.launch {
                                e.message?.let {
                                    snackbarHostState.showSnackbar(
                                        message = it
                                    )
                                }
                            }
                        }
                    }
                })
            )

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(80.dp, 0.dp)
            )
            {
                Button(
                    enabled = true,
                    onClick = requestAction,
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .width(100.dp)
                        .height(40.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.requestButton),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Button(
                    onClick = {
                        // Block the main thread until login
                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                viewModel.login()
                                loginAction()
                            } catch (e: Exception) {
                                scope.launch {
                                    e.message?.let {
                                        snackbarHostState.showSnackbar(
                                            message = it
                                        )
                                    }
                                }
                            }
                        }


                    },
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .width(100.dp)
                        .height(40.dp)
                ) { Text(text = stringResource(id = R.string.login)) }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Dialog for user that wish to reset their password
            val showDialog = remember { mutableStateOf(false) }
            if (showDialog.value) {
                ResetPasswordAlert(
                    viewModel = viewModel,
                    showDialog = showDialog,
                    scope = scope,
                    snackBarHostState = snackbarHostState,
                )
            }

            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.forgotpswd)),
                onClick = { showDialog.value = true },
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }
}

@Composable
private fun ResetPasswordAlert(
    viewModel: LoginScreenViewModel,
    showDialog: MutableState<Boolean>,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    val state = viewModel.uiState.collectAsState()
    focusManager.clearFocus()

    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        confirmButton = {
            TextButton(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        viewModel.resetPassword(state.value.email)
                        showDialog.value = false
                    } catch (e: Exception) {
                        scope.launch {
                            e.message?.let {
                                snackBarHostState.showSnackbar(
                                    message = it
                                )
                            }
                        }
                    }
                }
            }) {
                Text(text = stringResource(id = R.string.reset))
            }
        },
        dismissButton = {
            TextButton(onClick = { showDialog.value = false }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        title = { Text(text = stringResource(id = R.string.resetPassword)) },
        text = {
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.emailLabel)) },
                value = state.value.email,
                singleLine = true,
                onValueChange = { viewModel.onEmailChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
        }
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(LoginScreenViewModel(LoginRepository(LoginNetworkDataSource())), {}, {}, {})
}