package com.slgi.slgiapp.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UserRequestsScreen(
    viewModel: UserRequestsViewModel,
    bottomBar: @Composable () -> Unit,
) {
    val requests = viewModel.requests.collectAsState(initial = emptyList())
    Scaffold(
        bottomBar = bottomBar
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(items = requests.value) {
                ListItem(
                    headlineContent = {
                        Row {
                            Text(text = it.firstName)
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = it.lastName)
                        }
                    },
                    supportingContent = { Text(text = it.email) },
                    trailingContent = {
                        Row {
                            IconButton(onClick = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    try {
                                        viewModel.deleteUserRequest(it)
                                    } catch (e: Exception) {
                                        // TODO Implement logic for failed approval
                                    }
                                }
                            }) {
                                Icon(imageVector = Icons.Outlined.Cancel, contentDescription = " ")
                            }
                            IconButton(onClick = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    try {
                                        viewModel.approveUser(it)
                                    } catch (e: Exception) {
                                        // TODO Implement logic for failed approval
                                    }
                                }
                            }) {
                                Icon(imageVector = Icons.Outlined.Check, contentDescription = " ")
                            }
                        }
                    }
                )
            }
        }
    }
}