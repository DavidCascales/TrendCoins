package com.trendCoins.ui.features.tienda

data class ArticuloUiState(
    val id: Int = 0, val imagen: String = "", val descripcion: String = "",
    val precio: Int = 0,val tipo:String="", val favorito: Boolean = false
)


