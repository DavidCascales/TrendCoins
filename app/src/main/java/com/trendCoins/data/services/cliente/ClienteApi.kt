package com.pmdm.tienda.data.services.cliente


data class ClienteApi(
    val correo: String,
    val password:String,
    val nombre: String,
    val telefono: String,
    val imagen:String,
    val deseados:String,
    val calle: String?,
    val ciudad: String?,
    val puntos:Int)


