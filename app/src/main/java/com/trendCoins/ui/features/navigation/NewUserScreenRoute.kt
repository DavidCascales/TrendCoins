package com.pmdm.tienda.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pmdm.tienda.ui.features.newuser.NewUserScreenBuena
import com.pmdm.tienda.ui.features.newuser.NewUserViewModel
import com.pmdm.tienda.ui.features.newuser.direccion.DireccionEvent


const val NewUserRoute = "newUser"

fun NavGraphBuilder.newUserScreenRoute(
    newUserViewModel: NewUserViewModel,
    onNavigateToLogin: (correo: String, navOptions: NavOptions?) -> Unit
) {
    composable(NewUserRoute) {
/*
        NewUserScreen(
            esNuevoClienteState = newUserViewModel.esNuevoCliente,
            newUserUiState = newUserViewModel.newUserUiState,
            validacionNewUserUiState = newUserViewModel.validacionNewUserUiState,
            mostrarSnack = newUserViewModel.mostrarSnackState,
            mensaje = newUserViewModel.mensajeSnackBarState,
            incrementaPagina = newUserViewModel.incrementaPaginaState,
            onDireccionEvent = newUserViewModel::onDireccionEvent,
            onDatosPersonalesEvent = newUserViewModel::onDatosPersonalesEvent,
            onNewUserPasswordEvent = newUserViewModel::onNewUserPasswordEvent,
            onNavigateToLogin=onNavigateToLogin
        )*/

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

