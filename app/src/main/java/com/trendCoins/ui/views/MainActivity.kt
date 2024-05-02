package com.trendCoins.ui.views

import TiendaNavHost
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
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
import com.pmdm.tienda.ui.features.login.LoginScreen
import com.pmdm.tienda.ui.features.newuser.NewUserScreenBuena
import com.pmdm.tienda.ui.features.newuser.NewUserViewModel
import com.pmdm.tienda.ui.features.tienda.TiendaViewModel
import com.pmdm.tienda.ui.features.tienda.components.Escaparate
import com.pmdm.tienda.ui.navigation.navigateToLogin
import com.trendCoins.ui.theme.TrendCoinsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            TrendCoinsTheme {
                val vm:TiendaViewModel by viewModels()
                val newUserViewModel :NewUserViewModel by viewModels()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {



                    TiendaNavHost()
                    /*
                    Escaparate(
                        articulos = vm.articulosState?.toList()!!,
                        articuloSeleccionado = vm.articuloSeleccionadoState,
                        tallaUiState = vm.tallaUiState,
                        onTallaEvent = vm::onTallaEvent,
                        onTiendaEvent = vm::onTiendaEvent
                    )*/

                    /*NewUserScreenBuena(
                        newUserUiState = newUserViewModel.newUserUiState,
                        validacionNewUserUiState = newUserViewModel.validacionNewUserUiState,
                        esNuevoClienteState = newUserViewModel.esNuevoCliente,
                        mostrarSnack = newUserViewModel.mostrarSnackState,
                        mensaje = newUserViewModel.mensajeSnackBarState,
                        onNavigateToLogin =  TODO(),
                        onNewUserEvent = newUserViewModel::onNewUserEvent,
                        onFotoCambiada = {newUserViewModel.onFotoCambiada(it)}
                    )*/
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