package com.example.pokeapi_koin.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.pokeapi_koin.common.AppTopBar
import com.example.pokeapi_koin.common.ErrorState
import org.koin.androidx.compose.get

@Composable
fun PokemonDetailsScreen(
    navController: NavController,
    name: String,
    viewModel: PokemonDetailsViewModel = get()
) {
    // MARK: - State
    val pokemonDetails = viewModel.pokemonDetails.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val gotError = viewModel.gotError.collectAsState()
    val isFavorite = viewModel.isFavorite.collectAsState()

    LaunchedEffect(pokemonDetails) {
        viewModel.fetchDetails(name)
    }

    Scaffold(topBar = {
        AppTopBar(
            title = "Pokemon Details",
            shouldDisplayBackButton = true,
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.didClickFavorite()
                }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = if(isFavorite.value) Color.Red else Color.LightGray
                    )
                }
            },
            iconTapAction = { navController.popBackStack() }
        )
    }) {
        Content(
            modifier = Modifier.padding(it),
            isLoading = isLoading.value,
            gotError = gotError.value,
            pokemonDetails = pokemonDetails.value
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    gotError: Boolean,
    pokemonDetails: PokemonDetailsModel?
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(200.dp),
                color = Color.Blue,
                trackColor = Color.Red
            )
        } else if(gotError) {
            ErrorState()
        } else {
            pokemonDetails.let {details ->
                if (details != null) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(url = details.sprite.imageURL)
                        Text(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            text = details.name,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun AsyncImage(url: String) {
    val painter: Painter = rememberImagePainter(data = url,  builder = {
        // Optionally, you can apply transformations
        transformations(CircleCropTransformation())
    })
    Image(
        modifier = Modifier.size(200.dp),
        painter = painter,
        contentDescription = null
    )
}