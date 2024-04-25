package com.pmdm.tienda.ui.navigation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pmdm.tienda.ui.features.login.LoginEvent
import com.pmdm.tienda.ui.features.login.LoginScreen
import com.pmdm.tienda.ui.features.login.LoginViewModel

import com.pmdm.tienda.ui.features.newuser.newuserpassword.NewUserPasswordEvent


const val HomeRoute = "login?{correo}"

fun NavGraphBuilder.loginScreenRoute(
    loginViewModel: LoginViewModel,
    onNavigateToNewUser: () -> Unit,
    onNavigateToTienda: (correo: String) -> Unit,
) {

    composable(route = HomeRoute) { backStackEntry ->

        LoginScreen(
            usuarioUiState = loginViewModel.usuarioUiState,
            validacionLoginUiState = loginViewModel.validacionLoginUiState,
            onLoginEvent = loginViewModel::onLoginEvent,
            mostrarSnack = loginViewModel.mostrarSnackBar,
            onMostrarSnackBar = loginViewModel.onMostrarSnackBar,
            onNavigateToTienda = onNavigateToTienda,
            onNavigateToNewUser = onNavigateToNewUser
        )
    }
}

fun NavController.navigateToLogin(valorLogin: String, navOptions: NavOptions? = null) {
    val x = "login?$valorLogin"
    this.navigate(x, navOptions)
}