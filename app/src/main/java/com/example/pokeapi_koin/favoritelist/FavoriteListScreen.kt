package com.example.pokeapi_koin.favoritelist

import android.graphics.Paint.Align
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pokeapi_koin.common.AppTopBar
import com.example.pokeapi_koin.common.Favorite
import org.koin.androidx.compose.get

@Composable
fun FavoriteListScreen(
    navController: NavController,
    viewModel: FavoriteListViewModel = get()) {
    val favoriteList = viewModel.favoriteList.collectAsState()

    Scaffold(topBar = {
        AppTopBar(
            title = "Favorite Pokemon",
            shouldDisplayBackButton = true) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = Color.White
        ) {
            LazyColumn(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favoriteList.value) {item ->
                    FavoriteRow(favorite = item, clickAction = {
                        navController.navigate("details/${item.name}")
                    }) {
                        viewModel.didClickDelete(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteRow(
    modifier: Modifier = Modifier,
    favorite: Favorite,
    clickAction: (() -> Unit),
    deleteAction: (() -> Unit)
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable { clickAction() },
        shape = RoundedCornerShape(
            topStart = CornerSize(32.dp),
            topEnd = CornerSize(4.dp),
            bottomStart = CornerSize(32.dp),
            bottomEnd = CornerSize(32.dp)
        ),
        color = Color(0xFFADD8E6)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                favorite.name,
                fontSize = 20.sp
            )

            IconButton(onClick = deleteAction) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color(0x89FF1F0F)
                )
            }
        }
    }
}