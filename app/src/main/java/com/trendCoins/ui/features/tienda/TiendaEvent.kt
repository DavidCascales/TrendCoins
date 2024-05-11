package com.pmdm.tienda.ui.features.tienda

import com.trendCoins.models.ArticuloCarrito


sealed interface TiendaEvent {

    object OnClickSumaPuntosClicker : TiendaEvent
    data class OnClickArticulo(val articulo: ArticuloUiState) : TiendaEvent
    data class OnClickAñadirCesta(var articulo: ArticuloUiState) : TiendaEvent
    data class OnFiltroChange(var filtro: String) : TiendaEvent
    data class OnTallaChange(var talla: String) : TiendaEvent
    data class OnClickFiltrar(var filtro: String) : TiendaEvent
    data class OnClickFavorito(var articulo:ArticuloUiState) : TiendaEvent
    data class OnClickEstaFiltrando(var estaFiltrando: Boolean) : TiendaEvent
    data class OnClickMas(var articulo: ArticuloUiState) : TiendaEvent
    data class OnClickMenos(var articulo: ArticuloUiState) : TiendaEvent
    object OnDismissDialog : TiendaEvent
    object OnClickQuitarFiltro : TiendaEvent
    object OnClickListarFavoritos : TiendaEvent
    object OnClickCarrito : TiendaEvent
    object OnClickComprar: TiendaEvent
    object OnClickSalir:TiendaEvent
}