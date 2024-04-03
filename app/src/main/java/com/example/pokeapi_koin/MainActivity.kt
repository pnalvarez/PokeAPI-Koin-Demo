package com.example.pokeapi_koin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokeapi_koin.common.PokeAPIApp
import com.example.pokeapi_koin.list.PokemonListScreen
import com.example.pokeapi_koin.ui.theme.PokeAPIKoinTheme
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeAPIKoinTheme {
                PokeAPIApp()
            }
        }
    }
}