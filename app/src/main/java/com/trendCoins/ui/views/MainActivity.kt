package com.trendCoins.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pmdm.tienda.ui.features.tienda.TiendaViewModel
import com.pmdm.tienda.ui.features.tienda.components.Escaparate
import com.trendCoins.ui.theme.TrendCoinsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrendCoinsTheme {
                val vm:TiendaViewModel by viewModels()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Escaparate(
                        articulos = vm.articulosState?.toList()!!,
                        articuloSeleccionado = vm.articuloSeleccionadoState,
                        tallaUiState = vm.tallaUiState,
                        onTallaEvent = vm::onTallaEvent,
                        onTiendaEvent = vm::onTiendaEvent
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TrendCoinsTheme {
        Greeting("Android")
    }
}