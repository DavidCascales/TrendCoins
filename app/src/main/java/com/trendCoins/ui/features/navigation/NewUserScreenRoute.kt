package com.trendCoins.ui.features.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.trendCoins.ui.features.newuser.NewUserScreenBuena
import com.trendCoins.ui.features.newuser.NewUserViewModel


const val NewUserRoute = "newUser"

fun NavGraphBuilder.newUserScreenRoute(
    newUserViewModel: NewUserViewModel,
    onNavigateToLogin: (correo: String, navOptions: NavOptions?) -> Unit
) {
    composable(NewUserRoute) {


        NewUserScreenBuena(
            newUserUiState = newUserViewModel.newUserUiState,
            validacionNewUserUiState = newUserViewModel.validacionNewUserUiState,
            esNuevoClienteState = newUserViewModel.esNuevoCliente,
            mostrarSnack = newUserViewModel.mostrarSnackState,
            mensaje = newUserViewModel.mensajeSnackBarState,
            onNavigateToLogin = onNavigateToLogin,
            onNewUserEvent = newUserViewModel::onNewUserEvent,
            onFotoCambiada = {newUserViewModel.onFotoCambiada(it)}
        )
    }

}

fun NavController.navigateToNewUser() {
    this.navigate(NewUserRoute)

}

