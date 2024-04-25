package com.pmdm.tienda.ui.features.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.tienda.ui.composables.CircularImageFromResource
import com.pmdm.tienda.ui.composables.TextNewAccount
import com.pmdm.tienda.ui.features.login.components.UsuarioPassword
import com.trendCoins.R
import com.trendCoins.ui.theme.Purple40
import com.trendCoins.ui.theme.TrendCoinsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    usuarioUiState: LoginUiState,
    validacionLoginUiState: ValidacionLoginUiState,
    mostrarSnack: Boolean,
    onLoginEvent: (LoginEvent) -> Unit,
    onMostrarSnackBar: () -> Unit,
    onNavigateToTienda: ((correo: String) -> Unit)? = null,
    onNavigateToNewUser: () -> Unit,
) {

//    var mensaje by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    var recordarmeState by remember { mutableStateOf(false) }

    Box() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            CircularImageFromResource(
                idImageResource = R.drawable.loginfoto, contentDescription = "Imagen Login"
            )


            UsuarioPassword(modifier = Modifier.fillMaxWidth(),
                loginState = usuarioUiState.correo,
                passwordState = usuarioUiState.contraseña,
                validacionLogin = validacionLoginUiState.validacionLogin,
                validacionPassword = validacionLoginUiState.validacionPassword,
                recordarmeState = recordarmeState,
                onValueChangeLogin = {
                    onLoginEvent(LoginEvent.LoginChanged(it))
                },
                onValueChangePassword = {
                    onLoginEvent(LoginEvent.PasswordChanged(it))
                },
                onCheckedChanged = { recordarmeState = it },
                onClickLogearse = {
                    onLoginEvent(LoginEvent.OnClickLogearse(onNavigateToTienda))
                    onMostrarSnackBar()
                    scope.launch() {
                        delay(4000)
                        onMostrarSnackBar()
                  //      onNavigateToTienda?.invoke(usuarioUiState.login)

                    }
                })
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Text(
                "Olvidaste Password?",
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic,
                color = Purple40
            )
            Text("ó")
            TextNewAccount(onClick = {
                onLoginEvent(LoginEvent.OnClickNewUser(onNavigateToNewUser))
            })
        }
        if (mostrarSnack) {
            var mensaje = ""
            if (validacionLoginUiState.hayError) mensaje = validacionLoginUiState.mensajeError ?: ""
            else if (usuarioUiState.estaLogeado) mensaje =
                "Entrando a la APP con usuario ${usuarioUiState.correo}"
            else mensaje = "Error, no existe el usuario o contraseña incorrecta"
            Snackbar(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(text = mensaje)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val loginViewModel: LoginViewModel = viewModel()
    TrendCoinsTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {

            LoginScreen(usuarioUiState = loginViewModel.usuarioUiState,
                validacionLoginUiState = loginViewModel.validacionLoginUiState,
                onLoginEvent = loginViewModel::onLoginEvent,
                mostrarSnack = false,
                onNavigateToNewUser = {},
                onNavigateToTienda = {},
                onMostrarSnackBar = {})
        }
    }
}