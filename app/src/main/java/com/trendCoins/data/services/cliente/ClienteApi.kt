package com.pmdm.tienda.data.services.cliente


import com.google.gson.annotations.SerializedName
data class ClienteApi(
    val correo: String,
    val contraseña:String,
    val nombre: String,
    val telefono: String,
    val imagen:String,
    val deseados:String,
    val calle: String?,
    val ciudad: String?,
    val puntos:Int)


