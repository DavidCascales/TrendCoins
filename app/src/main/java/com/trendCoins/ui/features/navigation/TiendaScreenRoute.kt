package com.pmdm.tienda.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pmdm.tienda.ui.features.tienda.TiendaScreen
import com.pmdm.tienda.ui.features.tienda.TiendaViewModel

const val TiendaRoute = "tienda/{correo}"

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.tiendaScreenRoute(
    tiendaViewModel: TiendaViewModel,
    onNavigateToPedido: (dni:String)->Unit,
    onNavigateToNewUser:(login:String)->Unit,
    onNavigateToLogin:()->Unit
) {


    composable(TiendaRoute) {
        val correo = it.arguments?.getString("correo")
       requireNotNull(correo)
       if(correo!=null) tiendaViewModel.actualizaCliente(correo)
        TiendaScreen(
            clienteUiState = tiendaViewModel.clienteState!!,
            articulos = tiendaViewModel.articulosState?.toList()!!,
            muestraFavoritos = tiendaViewModel.mostrarFavoritoState,
            articuloSeleccionado = tiendaViewModel.articuloSeleccionadoState,
            filtro = tiendaViewModel.filtroState,
            estaFiltrando = tiendaViewModel.estaFiltrandoState,
            tallaUiState = tiendaViewModel.tallaUiState,
            carrito = tiendaViewModel.carritoState,
            numerArticulos = tiendaViewModel.numeroArticulosState,
            totalCompra = tiendaViewModel.totalCompraState,
            onTiendaEvent = tiendaViewModel::onTiendaEvent,
            pedido = tiendaViewModel.pedidoUiState,
            onTallaEvent = tiendaViewModel::onTallaEvent,
            onNavigateToPedido = onNavigateToPedido,
            onNavigateToNewUser = onNavigateToNewUser,
            onNavigateToLogin = onNavigateToLogin,
            screenState=tiendaViewModel.screenState,
            onScreenChange={ it -> tiendaViewModel.onChangeScreen(it)},
            listaRuleta = tiendaViewModel.resultadosRuleta,
            mostrarResultado = tiendaViewModel.verResultadoRuleta,
            onObtenerResultadoRuleta = {tiendaViewModel.onObtenerResultadoRuleta(it)},
            resultadoFinalRuleta = {tiendaViewModel.resultadoFinalRuleta(it)},
            sumaPuntosClicker={tiendaViewModel.sumaPuntosClicker()}
        )
    }
}

fun NavController.navigateToTienda(valorLogin: String, navOptions: NavOptions? = null) {
    this.navigate("tienda/$valorLogin", navOptions)
}




