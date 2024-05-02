package com.pmdm.tienda.ui.features.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.tienda.data.services.ApiServicesException
import com.trendCoins.data.ClienteRepository
import com.trendCoins.models.Cliente
import com.trendCoins.utilities.Encriptacion.toSHA256
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository, private val validadorLogin: ValidadorLogin
) : ViewModel() {

    var usuarioUiState by mutableStateOf(LoginUiState())
        private set
    var validacionLoginUiState by mutableStateOf(ValidacionLoginUiState())
        private set
    var mostrarSnackBar by mutableStateOf(false)
    val onMostrarSnackBar: () -> Unit by mutableStateOf({
        mostrarSnackBar = !mostrarSnackBar
    })

    fun onLoginEvent(loginEvent: LoginEvent) {
        when (loginEvent) {
            is LoginEvent.LoginChanged -> {
                usuarioUiState = usuarioUiState.copy(
                    correo = loginEvent.login
                )
                validacionLoginUiState = validacionLoginUiState.copy(
                    validacionLogin = validadorLogin.validadorLogin.valida(loginEvent.login)
                )
            }

            is LoginEvent.PasswordChanged -> {
                usuarioUiState = usuarioUiState.copy(
                    contraseña = loginEvent.password
                )
                validacionLoginUiState = validacionLoginUiState.copy(
                    validacionPassword = validadorLogin.validadorPassword.valida(loginEvent.password)
                )
            }

            is LoginEvent.OnClickNewUser -> {
                loginEvent.onNavigateToNewUser.invoke()
            }

            is LoginEvent.OnClickLogearse -> {
                viewModelScope.launch {
                    validacionLoginUiState = validadorLogin.valida(usuarioUiState)
                    if (!validacionLoginUiState.hayError) {

                        usuarioUiState = usuarioUiState.copy(
                            estaLogeado = logearse()
                        )
                        if (usuarioUiState.estaLogeado) {
                            delay(3000)
                            loginEvent.onNavigateTienda?.let { it(usuarioUiState.correo) }
                        }
                    }
                }
            }

            else -> {}
        }
    }

    suspend fun logearse(): Boolean {
        val usuario = usuarioUiState.toUsuario()
        try {
            clienteRepository.get().forEach {
                if (usuario.correo == it.correo && usuario.contraseña.toSHA256() == it.contraseña) return true
            }
        } catch (e: ApiServicesException) {
        }
        return false
    }

    fun iniciaUsuario(correo: String?) {
        if (correo != null) usuarioUiState = LoginUiState( correo = "",
            contraseña = "",
            nombre = "",
            telefono = "",
            imagen = "",
            deseados = mutableListOf(),
            calle = "",
            ciudad = "",
            puntos = 0,
            estaLogeado = false)
        else usuarioUiState = LoginUiState()
        mostrarSnackBar = false
    }

    fun clearLogin() {
        usuarioUiState = LoginUiState()
    }

    fun LoginUiState.toUsuario() = Cliente(this.correo,this.contraseña,this.nombre,this.telefono,this.imagen,this.deseados,this.calle,this.ciudad,this.puntos)
}