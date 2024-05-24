package com.trendCoins.ui.features.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.trendCoins.ui.features.tienda.TiendaScreen
import com.trendCoins.ui.features.tienda.TiendaViewModel

const val TiendaRoute = "tienda/{correo}"

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.tiendaScreenRoute(
    tiendaViewModel: TiendaViewModel,
    onNavigateToNewUser: (login: String) -> Unit,
    onNavigateToLogin: () -> Unit
) {


    composable(TiendaRoute) {
        val correo = it.arguments?.getString("correo")
        requireNotNull(correo)
        if (correo != null && tiendaViewModel.sesionIniciada == false) tiendaViewModel.actualizaCliente(
            correo
        )
        TiendaScreen(
            clienteUiState = tiendaViewModel.clienteState!!,
            articulos = tiendaViewModel.articulosState?.toList()!!,
            deseados = tiendaViewModel.articulosFavoritosState.toList()!!,
            articuloSeleccionado = tiendaViewModel.articuloSeleccionadoState,
            filtro = tiendaViewModel.filtroState,
            estaFiltrando = tiendaViewModel.estaFiltrandoState,
            totalCompra = tiendaViewModel.totalCompra,
            onTiendaEvent = tiendaViewModel::onTiendaEvent,
            onNavigateToNewUser = onNavigateToNewUser,
            onNavigateToLogin = onNavigateToLogin,
            screenState = tiendaViewModel.screenState,
            onScreenChange = { it -> tiendaViewModel.onChangeScreen(it) },
            listaRuleta = tiendaViewModel.resultadosRuleta,
            verResultadoRuleta = tiendaViewModel.verResultadoRuleta,
            puntosRuleta = tiendaViewModel.puntosRuleta,
            listaArticuloCarrito = tiendaViewModel.listaArticuloCarrito?.toList()!!,
            mostrarCarrito = tiendaViewModel.mostrarCarrito

            )
    }
}

fun NavController.navigateToTienda(valorLogin: String, navOptions: NavOptions? = null) {
    this.navigate("tienda/$valorLogin", navOptions)
}




