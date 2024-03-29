package com.slgi.slgiapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoAccounts
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.slgi.slgiapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UserRequestsScreen(
    viewModel: UserRequestsViewModel,
    bottomBar: @Composable () -> Unit,
) {
    val requests = viewModel.requests.collectAsStateWithLifecycle(initialValue = emptyList())

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = bottomBar,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        if (requests.value.isNotEmpty()) {
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
                                    CoroutineScope(Dispatchers.IO).launch {
                                        try {
                                            viewModel.deleteUserRequest(it)
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
                                }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Cancel,
                                        contentDescription = null
                                    )
                                }
                                IconButton(onClick = {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        try {
                                            viewModel.approveUser(it)
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
                                }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Check,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.NoAccounts,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(text = stringResource(id = R.string.noNewRequests))
                }
            }
        }
    }
}