package com.example.pokeapi_koin.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    shouldDisplayBackButton: Boolean,
    trailingIcon: @Composable (() -> Unit) = { },
    iconTapAction: (() -> Unit)
) {
    Surface(
        shadowElevation = 4.dp,
        color = Color.White
    ) {
        CenterAlignedTopAppBar(title = {
            Text(
                title, fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }, navigationIcon = {
            if(shouldDisplayBackButton)
                IconButton(
                    onClick = { iconTapAction() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
        }, actions = {
            trailingIcon()
        })
    }
}