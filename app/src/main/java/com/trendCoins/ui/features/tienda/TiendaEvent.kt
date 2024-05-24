package com.trendCoins.ui.features.tienda

import com.trendCoins.models.ArticuloCarrito


sealed interface TiendaEvent {

    object OnClickSumaPuntosClicker : TiendaEvent
    object OnCompraRealizada: TiendaEvent
    data class onClickPuntosRuleta(var indicePuntos: Int) : TiendaEvent
    object OnchangeResultadoRuleta : TiendaEvent
    data class OnClickArticulo(val articulo: ArticuloUiState) : TiendaEvent
    data class OnClickAñadirCesta(var articulo: ArticuloUiState) : TiendaEvent
    data class OnFiltroChange(var filtro: String) : TiendaEvent
    data class OnTallaChange(var talla: String) : TiendaEvent
    data class OnClickFiltrar(var filtro: String) : TiendaEvent
    data class OnClickFavorito(var articulo: ArticuloUiState) : TiendaEvent
    data class OnClickEstaFiltrando(var estaFiltrando: Boolean) : TiendaEvent
    data class OnClickMas(var articulo: ArticuloCarrito) : TiendaEvent
    data class OnClickMenos(var articulo: ArticuloCarrito) : TiendaEvent
    object OnDismissDialog : TiendaEvent
    object OnClickQuitarFiltro : TiendaEvent

    object OnClickSalir : TiendaEvent
}