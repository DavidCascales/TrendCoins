package com.trendCoins.ui.features.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.trendCoins.ui.features.login.LoginScreen
import com.trendCoins.ui.features.login.LoginViewModel


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