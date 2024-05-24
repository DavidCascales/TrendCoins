package com.trendCoins.ui.features.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable


const val PedidoRoute = "pedido/{dni}"

fun NavController.navigateToPedido(valorDni: String, navOptions: NavOptions? = null) {
    this.navigate("pedido/$valorDni", navOptions)
}


