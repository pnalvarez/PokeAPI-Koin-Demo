package com.example.pokeapi_koin.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    onClickAction: (() -> Unit)
) {
    IconButton(
        modifier = modifier,
        onClick = onClickAction
    ) {
        Surface(
            shape = CircleShape,
            color = Color.Blue
        ) {
            Icon(
                modifier = Modifier.padding(8.dp),
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}