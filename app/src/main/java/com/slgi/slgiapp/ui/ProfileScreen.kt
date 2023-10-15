package com.slgi.slgiapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slgi.slgiapp.R
import com.slgi.slgiapp.ui.shared.TopBar
import com.slgi.slgiapp.ui.theme.SLGIAppTheme

@Composable
fun ProfileScreen(
    bottomBar: @Composable () -> Unit,
    navigationMap: Map<Int, Pair<ImageVector, () -> Unit>>,
    goBackAction: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                barTitle = stringResource(R.string.Settings),
                goBackAction = goBackAction
            )
        },
        bottomBar = bottomBar
    )
    { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Profile(name = "") }
            items(navigationMap.keys.toList()) { entry ->
                ListItem(
                    leadingContent = {
                        Icon(
                            navigationMap[entry]?.first ?: Icons.Default.BrokenImage,
                            contentDescription = null
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = entry)
                        )
                    },
                    trailingContent = { Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, null) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .clickable(onClick = { navigationMap[entry]?.second?.let { it() } })
                )
            }
        }
    }
}

@Composable
fun Profile(name: String) {
    Spacer(modifier = Modifier.height(50.dp))
    Icon(
        Icons.Default.AccountCircle,
        contentDescription = null,
        modifier = Modifier.size(120.dp)
    )
    Text(text = name, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
    Spacer(modifier = Modifier.height(50.dp))
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    SLGIAppTheme(dynamicColor = false) {
        Surface {
            ProfileScreen(
                {},
                mapOf(
                    R.string.logout to Pair(Icons.AutoMirrored.Outlined.Logout) {}
                ), {})
        }
    }
}