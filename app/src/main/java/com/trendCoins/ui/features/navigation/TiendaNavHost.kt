import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pmdm.tienda.ui.features.login.LoginViewModel
import com.pmdm.tienda.ui.features.newuser.NewUserViewModel
import com.pmdm.tienda.ui.features.pedidos.PedidosViewModel
import com.pmdm.tienda.ui.features.tienda.TiendaViewModel
import com.pmdm.tienda.ui.navigation.HomeRoute

import com.pmdm.tienda.ui.navigation.loginScreenRoute
import com.pmdm.tienda.ui.navigation.navigateToLogin
import com.pmdm.tienda.ui.navigation.navigateToNewUser
import com.pmdm.tienda.ui.navigation.navigateToPedido
import com.pmdm.tienda.ui.navigation.navigateToTienda
import com.pmdm.tienda.ui.navigation.newUserScreenRoute
import com.pmdm.tienda.ui.navigation.pedidosScreenRoute
import com.pmdm.tienda.ui.navigation.tiendaScreenRoute


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TiendaNavHost() {
    val navController = rememberNavController()
    val newUserViewModel: NewUserViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val tiendaViewModel: TiendaViewModel = hiltViewModel()
    val pedidosViewModel: PedidosViewModel = hiltViewModel()
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
            onNavigateToPedido = { dni ->
                navController.navigateToPedido(dni)
            },
            onNavigateToNewUser = {
                newUserViewModel.esNuevoCliente=false
                newUserViewModel.inicializarCliente(tiendaViewModel.clienteState!!)
                navController.navigateToNewUser()
            },
            onNavigateToLogin = {
                navController.navigateToLogin("")
            },

        )
        pedidosScreenRoute(pedidosViewModel=pedidosViewModel)
    }
}