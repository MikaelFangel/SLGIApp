package com.slgi.slgiapp.ui.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.slgi.slgiapp.ui.theme.SLGIAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(barTitle: String) {
    TopAppBar(title = {
        Text(text = barTitle)
    },
        navigationIcon = {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    SLGIAppTheme(dynamicColor = false) {
        Surface() {
            TopBar("Profil Oplysninger")
        }
    }
}