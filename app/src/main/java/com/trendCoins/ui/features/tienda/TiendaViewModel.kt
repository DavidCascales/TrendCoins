package com.trendCoins.ui.features.tienda

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trendCoins.data.ArticuloCarritoRepository
import com.trendCoins.data.ClienteRepository
import com.trendCoins.data.ArticuloRepository
import com.trendCoins.models.Articulo
import com.trendCoins.models.ArticuloCarrito
import com.trendCoins.models.Cliente
import com.trendCoins.utilities.Email
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TiendaViewModel @Inject constructor(
    private val articuloRepository: ArticuloRepository,
    private val clienteRepository: ClienteRepository,
    private val articuloCarritoRepository: ArticuloCarritoRepository
) : ViewModel() {

    val resultadosRuleta by
    mutableStateOf(
        listOf(
            "10",
            "5",
            "1",
            "20",
            "100",
            "33",
            "5",
            "0"
        )
    )
    var mostrarCarrito by mutableStateOf(false)
    var totalCompra by mutableStateOf(0)
    var verResultadoRuleta by mutableStateOf(false)
    var puntosRuleta by mutableStateOf(0)
    var talla by mutableStateOf("")
    var puntos by mutableStateOf(0)

    var clienteState by mutableStateOf(Cliente())

    var articuloCarritoState by mutableStateOf(ArticuloCarrito())
    var screenState by mutableStateOf(0)

    var sesionIniciada by mutableStateOf(false)


    fun getTotalCompra() {
        totalCompra = listaArticuloCarrito?.map { a -> a.cantidad * a.precio }?.sum() ?: 0
    }

    fun onChangeScreen(indexScreen: Int) {
        screenState = indexScreen
    }


    fun sumaPuntosClicker() {
        viewModelScope.launch {
            clienteState = clienteState.copy(puntos = clienteState.puntos + 1)
            clienteRepository.update(clienteState)
        }

    }

    fun getPuntosusuario(): Int {
        return clienteState.puntos
    }


    fun ObtenerResultadoRuleta(resultado: Int): Int {
        return resultadosRuleta[resultado].toInt()

    }

    var filtroState by mutableStateOf("")


    var estaFiltrandoState by mutableStateOf(false)


    var listaArticuloCarrito by mutableStateOf(mutableListOf<ArticuloCarrito>()?.toMutableStateList())
    var mostrarFavoritoState by mutableStateOf(false)


    var articulosState by mutableStateOf(mutableListOf<ArticuloUiState>().toMutableStateList())
    var articulosFavoritosState by mutableStateOf(mutableListOf<ArticuloUiState>().toMutableStateList())


    var articuloSeleccionadoState: ArticuloUiState? by mutableStateOf(null)
        private set

    var mail by mutableStateOf(Email)

    init {
        viewModelScope.launch {
            articulosState = getArticulos()
            clienteState = inicializaCliente(null)

        }
    }


    fun onTiendaEvent(tiendaEvent: TiendaEvent) {
        when (tiendaEvent) {

            is TiendaEvent.OnSesioniIciada -> {
                sesionIniciada!=sesionIniciada
            }

            is TiendaEvent.OnCompraRealizada -> {
                viewModelScope.launch {
                    clienteState = clienteState.copy(puntos = clienteState.puntos - totalCompra)

                    updateCliente()
                    mail.sendEmailCompraDetails(clienteState, listaArticuloCarrito, totalCompra)
                    val articulos = listaArticuloCarrito
                    articulos?.forEach {
                        articuloCarritoRepository.deleteArticulo(
                            clienteState.correo,
                            it.descripcion,
                            it.talla
                        )
                    }
                    listaArticuloCarrito = mutableListOf<ArticuloCarrito>().toMutableStateList()
                    mostrarCarrito = false
                    actualizarCifrasPedido()

                }
            }

            is TiendaEvent.OnTallaChange -> {
                talla = tiendaEvent.talla
            }

            is TiendaEvent.OnClickSumaPuntosClicker -> {
                clienteState = clienteState.copy(puntos = clienteState.puntos + 1)

            }

            is TiendaEvent.onClickPuntosRuleta -> {

                verResultadoRuleta = true
                puntosRuleta = ObtenerResultadoRuleta(tiendaEvent.indicePuntos)
                clienteState = clienteState.copy(puntos = clienteState.puntos + puntosRuleta)


            }

            is TiendaEvent.OnchangeResultadoRuleta -> {
                verResultadoRuleta = false
            }


            is TiendaEvent.OnClickArticulo -> {
                if (articuloSeleccionadoState?.id == tiendaEvent.articulo.id) articuloSeleccionadoState =
                    null
                else articuloSeleccionadoState = tiendaEvent.articulo
            }

            is TiendaEvent.OnDismissDialog -> {
                articuloSeleccionadoState = null
            }

            is TiendaEvent.OnClickAñadirCesta -> {
                viewModelScope.launch {
                    articuloCarritoState = tiendaEvent.articulo.toArticuloCarrito()
                    if (articuloCarritoState != null) {
                        //var seleccion: TipoTalla = TipoTalla.NOTALLA


                        if (talla != "") {
                            articuloCarritoState =
                                articuloCarritoState.copy(talla = talla)
                            val articuloAux = buscaArticuloEnCarrito(articuloCarritoState)
                            if (articuloAux != null) {
                                val posicion = buscaPosicionArticuloArticulo(articuloAux)
                                listaArticuloCarrito!![posicion!!] =
                                    listaArticuloCarrito!![posicion].copy(
                                        cantidad = listaArticuloCarrito!![posicion]?.cantidad!! + 1
                                    )
                                updateCarritoArticulo(posicion!!)
                            } else {
                                listaArticuloCarrito?.add(articuloCarritoState)
                                insertArticuloCarrito(articuloCarritoState)
                            }

                            actualizarCifrasPedido()
                            talla = "";
                        }
                    }
                }
                mostrarCarrito = true
            }

            is TiendaEvent.OnFiltroChange -> {
                filtroState = tiendaEvent.filtro
            }

            is TiendaEvent.OnClickFiltrar -> {
                viewModelScope.launch {
                    articulosState = getArticulos(tiendaEvent.filtro) ?: getArticulos()
                }
            }

            is TiendaEvent.OnClickQuitarFiltro -> {
                viewModelScope.launch {
                    articulosState = getArticulos()
                    filtroState = ""
                    estaFiltrandoState = false
                    mostrarFavoritoState = false
                }
            }

            is TiendaEvent.OnClickEstaFiltrando -> {
                estaFiltrandoState = tiendaEvent.estaFiltrando
            }

            is TiendaEvent.OnClickFavorito -> {
                viewModelScope.launch {
                    val posicion =
                        articulosState.indexOf(articulosState.find { a -> tiendaEvent.articulo.id == a.id })
                    if (posicion != -1) {
                        val favorito = articulosState[posicion].favorito
                        articulosState[posicion] =
                            articulosState[posicion].copy(favorito = !favorito)

                        if (!clienteState.deseados.contains(tiendaEvent.articulo.id)) clienteState.deseados.add(
                            tiendaEvent.articulo.id
                        )
                        else clienteState.deseados.remove(tiendaEvent.articulo.id)
                        actualizaFavoritos()
                    }
                }

            }


            is TiendaEvent.OnClickMas -> {
                viewModelScope.launch {
                    val posicion = buscaPosicionArticuloArticulo(tiendaEvent.articulo)
                    listaArticuloCarrito?.set(
                        posicion!!,
                        listaArticuloCarrito!![posicion].copy(cantidad = listaArticuloCarrito!![posicion].cantidad + 1)
                    )
                    actualizarCifrasPedido()
                    updateCarritoArticulo(posicion!!)
                }
            }

            is TiendaEvent.OnClickMenos -> {
                viewModelScope.launch {
                    val posicion = buscaPosicionArticuloArticulo(tiendaEvent.articulo)
                    if (listaArticuloCarrito!![posicion!!].cantidad - 1 > 0) {
                        listaArticuloCarrito!![posicion] =
                            listaArticuloCarrito!![posicion].copy(cantidad = listaArticuloCarrito!![posicion].cantidad - 1)
                        updateCarritoArticulo(posicion!!)
                    } else {
                        listaArticuloCarrito!!.removeAt(posicion)
                        if (listaArticuloCarrito!!.isEmpty()) {
                            mostrarCarrito = false
                        }
                        deleteArticuloCarrito(tiendaEvent.articulo)
                    }
                    actualizarCifrasPedido()

                }
            }


            is TiendaEvent.OnClickSalir -> {
                sesionIniciada = false
                viewModelScope.launch {
                    updateCliente()
                    clearTienda()
                }

            }


        }
    }

    suspend fun updateCliente() {
        clienteRepository.update(clienteState)
    }


    suspend private fun getArticulos() =
        articuloRepository.get().toMutableList().toArticulosUiState().checkFavoritos()
            .toMutableStateList()


    suspend private fun getArticulos(filtro: String) =
        articuloRepository.get(filtro)?.toMutableList()?.toArticulosUiState()?.checkFavoritos()
            ?.toMutableStateList()


    suspend private fun getArticulosFavoritos(): MutableList<ArticuloUiState> {
        val lista = clienteRepository.getClienteCorreo(clienteState.correo).deseados
        return getArticulos().filter { lista.contains(it.id) }.toMutableList().checkFavoritos()
    }

    suspend private fun actualizaFavoritos() {
        clienteRepository.actualizaFavoritos(clienteState.correo, clienteState.deseados)
        // clienteState = clienteRepository.get(clienteState.correo)
        articulosFavoritosState = getArticulosFavoritos().toMutableStateList()

    }


    private fun buscaArticuloEnCarrito(articulo: ArticuloCarrito): ArticuloCarrito? {
        return listaArticuloCarrito?.find { a ->
            a.descripcion == articulo.descripcion && a.talla == articulo.talla
        }
    }

    private fun buscaPosicionArticuloArticulo(articulo: ArticuloCarrito): Int? {
        return listaArticuloCarrito?.indexOf(articulo)
    }

    private fun actualizarCifrasPedido() {
        totalCompra = listaArticuloCarrito?.map { a -> a.cantidad * a.precio }?.sum() ?: 0
    }

    suspend fun updateCarritoArticulo(posicion: Int) {
        articuloCarritoRepository.update(listaArticuloCarrito!![posicion])
    }

    suspend fun insertArticuloCarrito(articulo: ArticuloCarrito) {
        articuloCarritoRepository.insert(articulo)
    }

    suspend fun deleteArticuloCarrito(articulo: ArticuloCarrito) {
        articuloCarritoRepository.deleteArticulo(
            clienteState.correo,
            articulo.descripcion,
            articulo.talla
        )
    }

    suspend private fun getCliente(login: String) = clienteRepository.getClienteCorreo(login)

    private fun Articulo.toArticuloUiState() =
        ArticuloUiState(this.id, this.imagen, this.descripcion, this.precio, this.tipo, false)


    suspend fun inicializaCliente(correo: String?): Cliente {
        if (correo == null) return Cliente("", "", "", "", "", mutableListOf(), "", "", 0)
        else {
            val c = clienteRepository.getClienteCorreo(correo)

            return Cliente(
                c.correo,
                c.contraseña,
                c.nombre,
                c.telefono,
                c.imagen,
                c.deseados,
                c.calle,
                c.ciudad,
                c.puntos
            )
        }
    }

    fun actualizaCliente(correo: String) {

        sesionIniciada = true
        viewModelScope.launch {
            val c = clienteRepository.getClienteCorreo(correo)
            clienteState = Cliente(
                c.correo,
                c.contraseña,
                c.nombre,
                c.telefono,
                c.imagen,
                c.deseados,
                c.calle,
                c.ciudad,
                c.puntos
            )
            /*Mirar lo del update de los puntos*/
            puntos = clienteState.puntos

//            pedidoUiState = pedidoUiState.copy(dniCliente = c.dni)
            if (clienteState.deseados.size > 0) {
                articulosState =
                    articulosState.checkFavoritos().toMutableStateList()

                articulosFavoritosState = checkSoloFavoritos().toMutableStateList()

            }

            listaArticuloCarrito =
                articuloCarritoRepository.get(c.correo)?.toMutableStateList()

            if (listaArticuloCarrito!!.isNotEmpty()) {
                mostrarCarrito = true;
                actualizarCifrasPedido()
            }
        }

    }

    fun clearTienda() {
        viewModelScope.launch {
            clienteState = inicializaCliente(null)
            filtroState = ""
            estaFiltrandoState = false
            articuloSeleccionadoState = null
            mostrarFavoritoState = false
            articulosState = getArticulos()
            sesionIniciada=false

        }
    }

    private fun MutableList<ArticuloUiState>.checkFavoritos(): MutableList<ArticuloUiState> {
        var listaChecked = mutableListOf<ArticuloUiState>()
        this.forEach {
            if (clienteState != null && clienteState?.deseados?.contains(it.id)!!) listaChecked.add(
                ArticuloUiState(
                    it.id, it.imagen, it.descripcion, it.precio, it.tipo, true
                )
            )
            else listaChecked.add(it)
        }
        return listaChecked
    }

    private fun checkSoloFavoritos(): MutableList<ArticuloUiState> {
        var listaChecked = mutableListOf<ArticuloUiState>()
        articulosState.forEach {
            if (clienteState != null && clienteState?.deseados?.contains(it.id)!!) listaChecked.add(
                ArticuloUiState(
                    it.id, it.imagen, it.descripcion, it.precio, it.tipo, true
                )
            )
        }
        return listaChecked
    }

    fun ArticuloUiState.toArticuloCarrito() =
        ArticuloCarrito(
            this.id,
            correoCliente = clienteState.correo,
            this.descripcion,
            this.precio,
            1,
            talla
        )

    private fun MutableList<Articulo>.toArticulosUiState() =
        this.map { it.toArticuloUiState() }.toMutableList()

}



