package com.trendCoins.ui.features.login

import androidx.compose.runtime.mutableStateListOf

data class LoginUiState(
    val correo: String,
    val contraseña: String,
    val nombre: String,
    val telefono: String,
    val imagen: String,
    val deseados: MutableList<Int>,
    val calle: String,
    val ciudad: String,
    val puntos: Int,
    val estaLogeado: Boolean
) {
    constructor() : this(

        correo = "",
        contraseña = "",
        nombre = "",
        telefono = "",
        imagen = "",
        deseados = mutableListOf(),
        calle = "",
        ciudad = "",
        puntos = 0,
        estaLogeado = false

    )
}
