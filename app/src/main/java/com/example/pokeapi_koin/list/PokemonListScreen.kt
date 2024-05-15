package com.example.pokeapi_koin.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokeapi_koin.common.ErrorState
import com.example.pokeapi_koin.common.FavoriteButton
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = get()
) {
    val pagingData = viewModel.pagingData.collectAsLazyPagingItems()

    Scaffold(topBar = {
        Surface(
            shadowElevation = 4.dp,
            color = Color.White
        ) {
            CenterAlignedTopAppBar(title = {
                Text(
                    "Pokemon List",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            })
        }
    }, floatingActionButton = {
        FavoriteButton(
            modifier = Modifier
                .padding(bottom = 16.dp)
        ) {
            // TO DO
        }
    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn {
                    // Iterate through paging data list
                    items(pagingData.itemCount) {
                        val name = pagingData[it]?.name ?: ""
                        PokemonCell(
                            index = "${it+1}",
                            name = name
                        ) {
                            navController.navigate("details/$name")
                        }
                    }
                    // Check refreshing state for paging data
                    pagingData.apply {
                        when {
                            // FIRST LOAD
                            loadState.refresh is LoadState.Loading -> {
                                item { CircularProgressIndicator() }
                            }

                            // GOT ERROR ON FIRST LOAD
                            loadState.refresh is LoadState.Error -> {
                                item {
                                    ErrorState()
                                }
                            }

                            // LOADING A NEXT PAGE
                            loadState.append is LoadState.Loading -> {
                                item { CircularProgressIndicator() }
                            }

                            // GOT AN ERROR AFTER LOADING SOME SUBSEQUENT PAGE
                            loadState.append is LoadState.Error -> {
                                item {
                                    ErrorState()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PokemonCell(
    modifier: Modifier = Modifier,
    index: String,
    name: String,
    onClick: () -> Unit
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(top = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start) {
        Row(
            modifier = Modifier.padding(start = 20.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = index, fontSize = 20.sp)
            Text(text = name, fontSize = 20.sp, modifier = Modifier.padding(start = 16.dp))
        }
        Divider(color = Color.Gray, modifier = Modifier.padding(top = 20.dp))
    }
}