package com.slgi.slgiapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.slgi.slgiapp.R

@Composable
fun RegistrationScreen() {
    Scaffold (
        topBar = { /* TODO Add topbar */ }
    ) {innerPadding ->
        Column(
            Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                label = { Text(text = "Fornavn") },
                value = "", onValueChange = {})
            OutlinedTextField(
                label = { Text(text = "Efternavn") },
                value = "", onValueChange = {})
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.emailLabel)) },
                value = "", onValueChange = {})
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.passwordLable)) },
                value = "", onValueChange = {})
            OutlinedTextField(
                label = { Text(text = "Password igen") },
                value = "", onValueChange = {})
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = false, onCheckedChange = {})
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Jeg acceptere betignelserne.")
            }
            Button(
                onClick = { /*TODO*/ }) {
                Text(text = "Anmod om adgang")
            }
        }
    }
}