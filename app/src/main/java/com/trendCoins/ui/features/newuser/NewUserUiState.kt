package com.trendCoins.ui.features.newuser



data class NewUserUiState(

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