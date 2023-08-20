package com.slgi.slgiapp.ui

import android.os.DeadObjectException
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slgi.slgiapp.R
import com.slgi.slgiapp.data.LoginNetworkDataSource
import com.slgi.slgiapp.data.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    loginAction: () -> Unit,
    requestAction: () -> Unit,
    forgotPasswordAction: (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = stringResource(id = R.string.organization_name),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
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
            onValueChange = { viewModel.onEmailChange(it) }
        )

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            label = { Text(text = stringResource(id = R.string.passwordLable)) },
            value = uiState.value.password,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { viewModel.onPasswordChange(it) },
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Outlined.Visibility
                else Icons.Outlined.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
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
                onClick = requestAction,
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp)
            ) { Text(text = stringResource(id = R.string.requestButton), color = MaterialTheme.colorScheme.primary) }
            Button(
                onClick = {
                    // Block the main thread until login
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            viewModel.login()
                            loginAction()
                        } catch (e: Exception) {
                            // TODO Implement logic for failed login
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
        ClickableText(
            text = AnnotatedString(stringResource(id = R.string.forgotpswd)),
            onClick = forgotPasswordAction,
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(LoginScreenViewModel(LoginRepository(LoginNetworkDataSource())), {}, {}, {})
}