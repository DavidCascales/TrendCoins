package com.pmdm.tienda.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pmdm.tienda.ui.features.pedidos.PedidosScreen
import com.pmdm.tienda.ui.features.pedidos.PedidosViewModel


const val PedidoRoute = "pedido/{dni}"

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.pedidosScreenRoute(
    pedidosViewModel: PedidosViewModel
) {

    composable(PedidoRoute) {
        val dni = it.arguments?.getString("dni")
        if(dni!=null) pedidosViewModel.actualizaPedido(dni)
        requireNotNull(dni)
        PedidosScreen(
            pedidos = pedidosViewModel.pedidosUiState!!,
            pedidoSeleccionado = pedidosViewModel.pedidoSeleccionadoUiState,
            onClickPedido = pedidosViewModel.pedidoSeleccionadoEvent,
            onClickMuestraPedido = pedidosViewModel.muestraPedidoEvent,
            muestraPedidos = pedidosViewModel.muestraPedidos,
        )
    }
}

fun NavController.navigateToPedido(valorDni: String, navOptions: NavOptions? = null) {
    this.navigate("pedido/$valorDni", navOptions)
}


