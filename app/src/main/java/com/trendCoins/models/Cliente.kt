package com.trendCoins.models

data class Cliente(
    val correo: String = "",
    val contraseña: String = "",
    val nombre: String = "",
    val telefono: String = "",
    val imagen: String? = "",
    val deseados: MutableList<Int> = mutableListOf(),
    val calle: String = "",
    val ciudad: String = "",
    val puntos: Int = 0
)