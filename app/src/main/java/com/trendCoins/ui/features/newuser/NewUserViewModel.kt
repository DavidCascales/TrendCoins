package com.trendCoins.ui.features.newuser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.pmdm.agenda.utilities.imagenes.Imagenes
import com.trendCoins.data.services.ApiServicesException
import com.trendCoins.ui.features.navigation.HomeRoute
import com.trendCoins.data.ClienteRepository
import com.trendCoins.models.Cliente
import com.trendCoins.utilities.Encriptacion.toSHA256
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewUserViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository,
    private val validadorNewUser: ValidadorNewUser
) : ViewModel() {
    var esNuevoCliente by mutableStateOf(true)
    var mostrarSnackState by mutableStateOf(false)
    var mensajeSnackBarState by mutableStateOf("")

    //var incrementaPaginaState by mutableStateOf(0)
    var estaCreadaCuenta: Boolean = false

    var newUserUiState by mutableStateOf(NewUserUiState())
    var validacionNewUserUiState by mutableStateOf(ValidacionNewUserUiState())

    fun onFotoCambiada(image: ImageBitmap) {
        /*hacer lo del copy*/
        newUserUiState =
            newUserUiState.copy(imagen = Imagenes.androidBitmapToBase64(image.asAndroidBitmap()))
    }

    fun onNewUserEvent(event: NewUserEvent) {
        when (event) {
            is NewUserEvent.LoginChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(

                    correo = event.login

                )
                validacionNewUserUiState = validacionNewUserUiState.copy(

                    validacionLogin = validadorNewUser.validacionLogin.valida(event.login)

                )

            }

            is NewUserEvent.CalleChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(

                    calle = event.calle

                )
                validacionNewUserUiState = validacionNewUserUiState.copy(

                    validacionCalle = validadorNewUser.validadorCalle.valida(event.calle)

                )
            }

            is NewUserEvent.CiudadChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(

                    ciudad = event.ciudad,

                    )
                validacionNewUserUiState = validacionNewUserUiState.copy(

                    validacionCiudad = validadorNewUser.validadorCiudad.valida(event.ciudad)

                )
            }

            is NewUserEvent.NombreChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(
                    nombre = event.nombre
                )
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionNombre = validadorNewUser.validadorNombre.valida(event.nombre)

                )
            }

            is NewUserEvent.PasswordChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(

                    contraseña = event.password,

                    )
                validacionNewUserUiState = validacionNewUserUiState.copy(

                    validacionPassword = validadorNewUser.validacionPassword.valida(event.password)
                )
            }

            is NewUserEvent.TelefonoChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(

                    telefono = event.telefono

                )
                validacionNewUserUiState = validacionNewUserUiState.copy(

                    validacionTelefono = validadorNewUser.validadorTelefono.valida(event.telefono)

                )
            }

            is NewUserEvent.onClickCrearCliente -> {
                viewModelScope.launch {
                    mostrarSnackState = false
                    validacionNewUserUiState = validadorNewUser.valida(newUserUiState)
                    if (validacionNewUserUiState.hayError) {
                        mensajeSnackBarState = validacionNewUserUiState.mensajeError!!
                        mostrarSnackState = true
                    } else {
                        mostrarSnackState = true
                        creaCuenta()
                        if (estaCreadaCuenta) {
                            mensajeSnackBarState = "Cuenta creada correctamente"
                            var navOptions = NavOptions.Builder().apply {
                                setPopUpTo(
                                    HomeRoute, true, false
                                )
                            }
                            event.onNavigateToLogin?.invoke(
                                newUserUiState.correo,
                                navOptions.build()
                            )
                        } else mensajeSnackBarState = "Ese cliente ya existe"
                    }
                }
            }
        }
    }

    suspend private fun creaCuenta() {

        estaCreadaCuenta = false
        var clientes = mutableListOf<Cliente>()
        try {
            clientes = clienteRepository.get().toMutableList()
        } catch (e: ApiServicesException) {

        }

        val correoAnterior =
            clientes.find {
                it.correo == newUserUiState.correo
            }

        if (!esNuevoCliente && correoAnterior != null) {
            val cliente = editaCliente()
            clienteRepository.update(cliente.toCliente())
            estaCreadaCuenta = true
        } else if (correoAnterior == null) {
            val cliente = creaCliente()
            clienteRepository.insert(cliente.toCliente())
            estaCreadaCuenta = true
        }
    }

    private fun editaCliente(): NewUserUiState {

        val cliente = NewUserUiState(
            newUserUiState.correo,
            newUserUiState.contraseña,
            newUserUiState.nombre,
            newUserUiState.telefono,
            newUserUiState.imagen,
            newUserUiState.deseados,
            newUserUiState.calle,
            newUserUiState.ciudad,
            newUserUiState.puntos
        )
        return cliente
    }

    private fun creaCliente(): NewUserUiState {

        val cliente = NewUserUiState(
            newUserUiState.correo,
            newUserUiState.contraseña.toSHA256(),
            newUserUiState.nombre,
            newUserUiState.telefono,
            newUserUiState.imagen,
            newUserUiState.deseados,
            newUserUiState.calle,
            newUserUiState.ciudad,
            newUserUiState.puntos
        )
        return cliente
    }

    fun clearCliente() {
        newUserUiState = NewUserUiState()
        estaCreadaCuenta = false
        esNuevoCliente = true
    }

    fun inicializarCliente(cliente: Cliente) {
        viewModelScope.launch {
            newUserUiState = newUserUiState.copy(
                contraseña = cliente.contraseña, correo = cliente.correo,
                nombre = cliente.nombre, ciudad = cliente.ciudad,
                calle = cliente.calle, telefono = cliente.telefono,
                deseados = cliente.deseados, imagen = cliente.imagen,
                puntos = cliente.puntos
            )

        }
    }



    private fun NewUserUiState.toCliente() = Cliente(

        correo,
        contraseña,
        nombre,
        telefono,
        imagen,
        deseados,
        calle,
        ciudad,
        puntos

    )



}