import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.trendCoins.ui.features.login.LoginViewModel
import com.trendCoins.ui.features.newuser.NewUserViewModel
import com.trendCoins.ui.features.tienda.TiendaViewModel
import com.trendCoins.ui.features.navigation.HomeRoute

import com.trendCoins.ui.features.navigation.loginScreenRoute
import com.trendCoins.ui.features.navigation.navigateToLogin
import com.trendCoins.ui.features.navigation.navigateToNewUser
import com.trendCoins.ui.features.navigation.navigateToTienda
import com.trendCoins.ui.features.navigation.newUserScreenRoute
import com.trendCoins.ui.features.navigation.tiendaScreenRoute


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TiendaNavHost() {
    val navController = rememberNavController()
    val newUserViewModel: NewUserViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val tiendaViewModel: TiendaViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    )
    {
        loginScreenRoute(
            loginViewModel = loginViewModel,
            onNavigateToNewUser = {
                newUserViewModel.clearCliente()
                newUserViewModel.esNuevoCliente=true
                navController.navigateToNewUser()
                loginViewModel.clearLogin()
            },
            onNavigateToTienda = { correo ->
                tiendaViewModel.clearTienda()
                navController.navigateToTienda(correo)
                loginViewModel.clearLogin()
            },
        )
        newUserScreenRoute(
            newUserViewModel = newUserViewModel,
            onNavigateToLogin = { correo, navOptions ->
                if (correo != null) loginViewModel.iniciaUsuario(correo)
                navController.navigateToLogin(correo, navOptions)
                newUserViewModel.clearCliente()
            })
        tiendaScreenRoute(
            tiendaViewModel = tiendaViewModel,
            onNavigateToNewUser = {
                newUserViewModel.esNuevoCliente=false
                tiendaViewModel.sesionIniciada=false
                newUserViewModel.inicializarCliente(tiendaViewModel.clienteState!!)
                navController.navigateToNewUser()
            },
            onNavigateToLogin = {
                navController.navigateToLogin("")
            },

        )

    }
}