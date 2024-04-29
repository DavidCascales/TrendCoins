package com.pmdm.tienda.ui.features.newuser

import com.pmdm.tienda.ui.features.newuser.datospersonales.DatosPersonalesUiState
import com.pmdm.tienda.ui.features.newuser.direccion.DireccionUiState
import com.pmdm.tienda.ui.features.newuser.newuserpassword.LoginPasswordUiState

data class NewUserUiState(
    /*val datosPersonalesUiState: DatosPersonalesUiState = DatosPersonalesUiState(),
    val direccionUiState: DireccionUiState = DireccionUiState(),
    val newUserPasswordUiState: LoginPasswordUiState = LoginPasswordUiState(),
*/
    val correo: String ,
    val contraseña: String ,
    val nombre: String ,
    val telefono: String ,
    val imagen: String?,
    val deseados: MutableList<Int> ,
    val calle: String,
    val ciudad: String ,
    val puntos: Int


){
    constructor() : this(

        correo = "",
        contraseña = "",
        nombre = "",
        telefono = "",
        imagen = "",
        deseados = mutableListOf(),
        calle = "",
        ciudad = "",
        puntos = 0

    )
}