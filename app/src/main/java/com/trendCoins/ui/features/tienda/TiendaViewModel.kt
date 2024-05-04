package com.pmdm.tienda.ui.features.tienda

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trendCoins.data.ClienteRepository
import com.trendCoins.data.ArticuloRepository
import com.trendCoins.models.Articulo
import com.trendCoins.models.Cliente
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TiendaViewModel @Inject constructor(
    private val articuloRepository: ArticuloRepository,
    private val clienteRepository: ClienteRepository
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

    var verResultadoRuleta by mutableStateOf(false)

    var clienteState by mutableStateOf(Cliente())

    var screenState by mutableStateOf(0)

    fun onChangeScreen(indexScreen: Int) {
        screenState = indexScreen
    }

    fun getPuntosusuario(): Int {
        return clienteState.puntos
    }

    val onMostrarSnackBar: () -> Unit by mutableStateOf({
        verResultadoRuleta = !verResultadoRuleta
    })

    fun resultadoFinalRuleta(puntos: Int) {
        onMostrarSnackBar
        sumaPuntos(onObtenerResultadoRuleta(puntos))
        viewModelScope.launch {
            delay(2000)
            onMostrarSnackBar()
        }

    }

    fun sumaPuntos(puntos: Int) {
        clienteState = clienteState.copy(puntos = getPuntosusuario() + puntos)
    }

    fun onObtenerResultadoRuleta(resultado: Int): Int {
        return resultadosRuleta[resultado].toInt()

    }

    var filtroState by mutableStateOf("")
    var carritoState by mutableStateOf(false)
    var numeroArticulosState by mutableStateOf(0)
    var totalCompraState by mutableStateOf(0f)
    var estaFiltrandoState by mutableStateOf(false)

    var pedidoUiState by mutableStateOf(iniciarNuevoPedido())
    var articulodDePedidoUiState: ArticuloDePedidoUiState by mutableStateOf(ArticuloDePedidoUiState())
    var mostrarFavoritoState by mutableStateOf(false)


    var articulosState by mutableStateOf(mutableListOf<ArticuloUiState>().toMutableStateList())

    var tallaUiState: TallaUiState by mutableStateOf(inicializaTalla())
    var articuloSeleccionadoState: ArticuloUiState? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            articulosState = getArticulos()
            clienteState = inicializaCliente(null)
        }
    }

    fun onTallaEvent(tallaEvent: TallaEvent) {

        when (tallaEvent) {
            is TallaEvent.OnClickPequeña -> tallaUiState = inicializaTalla(TipoTalla.PEQUEÑA)

            is TallaEvent.OnClickMediana -> tallaUiState = inicializaTalla(TipoTalla.MEDIANA)

            is TallaEvent.OnClickGrande -> tallaUiState = inicializaTalla(TipoTalla.GRANDE)

            is TallaEvent.OnClickXGrande -> tallaUiState = inicializaTalla(TipoTalla.XGRANDE)
            else -> {}
        }
    }

    fun onTiendaEvent(tiendaEvent: TiendaEvent) {
        when (tiendaEvent) {
            is TiendaEvent.OnClickArticulo -> {
                if (articuloSeleccionadoState?.id == tiendaEvent.articulo.id) articuloSeleccionadoState =
                    null
                else articuloSeleccionadoState = tiendaEvent.articulo
            }

            is TiendaEvent.OnDismissDialog -> {
                articuloSeleccionadoState = null
            }

            is TiendaEvent.OnClickAñadirCesta -> {
                articulodDePedidoUiState = tiendaEvent.articulo
                if (articulodDePedidoUiState != null) {
                    var seleccion: TipoTalla = TipoTalla.NOTALLA

                    for (t in tallaUiState.tallaSeleccionada) if (t.value) seleccion = t.key
                    if (seleccion != TipoTalla.NOTALLA) {
                        articulodDePedidoUiState =
                            articulodDePedidoUiState.copy(tamaño = seleccion.ordinal.toShort())
                        val articuloAux = buscaArticuloEnPedido(articulodDePedidoUiState)
                        if (articuloAux != null) {
                            val posicion = buscaPosicionArticuloPedido(articuloAux)
                            pedidoUiState.articulos[posicion] =
                                pedidoUiState.articulos[posicion].copy(
                                    cantidad = articuloAux.cantidad + pedidoUiState.articulos[posicion].cantidad
                                )
                        } else pedidoUiState.articulos.add(articulodDePedidoUiState)

                        actualizarCifrasPedido()
                        tallaUiState = inicializaTalla()
                    }
                }
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
                    carritoState = false
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

            is TiendaEvent.OnClickListarFavoritos -> {
                viewModelScope.launch {
                    mostrarFavoritoState = !mostrarFavoritoState
                    if (mostrarFavoritoState)
                        articulosState = getArticulosFavoritos().toMutableStateList()
                    else articulosState = getArticulos()
                    carritoState = false
                }
            }

            is TiendaEvent.OnClickCarrito -> {
                if (carritoState == false) carritoState = true
            }

            is TiendaEvent.OnClickMas -> {
                val posicion = buscaPosicionArticuloPedido(tiendaEvent.articulo)
                pedidoUiState.articulos[posicion] =
                    pedidoUiState.articulos[posicion].copy(cantidad = pedidoUiState.articulos[posicion].cantidad + 1)
                actualizarCifrasPedido()
            }

            is TiendaEvent.OnClickMenos -> {
                val posicion = buscaPosicionArticuloPedido(tiendaEvent.articulo)
                if (pedidoUiState.articulos[posicion].cantidad - 1 > 0) {
                    pedidoUiState.articulos[posicion] =
                        pedidoUiState.articulos[posicion].copy(cantidad = pedidoUiState.articulos[posicion].cantidad - 1)
                } else pedidoUiState.articulos.removeAt(posicion)
                actualizarCifrasPedido()
            }

            is TiendaEvent.OnClickComprar -> {
                /*
                var codigoNuevoPedido = -1L
                viewModelScope.launch {
                    try {

                        codigoNuevoPedido = pedidoRepository.idPedidoAAñadir()
                    } catch (e: ApiServicesException) {
                        if (codigoNuevoPedido == -1L) codigoNuevoPedido = 1
                    }
                    pedidoUiState = pedidoUiState.copy(
                        pedidoId = codigoNuevoPedido
                    )
                    val p = pedidoUiState.toPedido()
                    pedidoRepository.insert(p)
                    numeroArticulosState = 0
                    pedidoUiState = iniciarNuevoPedido()
                    carritoState = false
                    actualizarCifrasPedido()
                }*/
            }

            is TiendaEvent.OnClickSalir -> {
                clearTienda()
            }

            else -> {}
        }
    }

    suspend private fun getArticulos() =
        articuloRepository.get().toMutableList().toArticulosUiState().checkFavoritos()
            .toMutableStateList()


    suspend private fun getArticulos(filtro: String) =
        articuloRepository.get(filtro)?.toMutableList()?.toArticulosUiState()?.checkFavoritos()
            ?.toMutableStateList()

    /*suspend private fun getArticulosFavoritos(): MutableList<ArticuloUiState>? {
        return articuloRepository.get(clienteState.deseados)?.toMutableList()?.toArticulosUiState()
            ?.checkFavoritos()
    }*/
    suspend private fun getArticulosFavoritos(): MutableList<ArticuloUiState> {
        val lista = clienteRepository.getClienteCorreo(clienteState.correo).deseados
        return getArticulos().filter { lista.contains(it.id) }.toMutableList()
    }

    suspend private fun actualizaFavoritos() {
        clienteRepository.actualizaFavoritos(clienteState.correo, clienteState.deseados)
        // clienteState = clienteRepository.get(clienteState.correo)
        articulosState =
            if (mostrarFavoritoState) getArticulosFavoritos().toMutableStateList() else getArticulos()

    }

    private fun inicializaTalla(): TallaUiState {
        var tallaSeleccionada = mutableMapOf<TipoTalla, Boolean>()
        tallaSeleccionada[TipoTalla.PEQUEÑA] = false
        tallaSeleccionada[TipoTalla.MEDIANA] = false
        tallaSeleccionada[TipoTalla.GRANDE] = false
        tallaSeleccionada[TipoTalla.XGRANDE] = false
        return TallaUiState(tallaSeleccionada)
    }

    private fun inicializaTalla(tipoTalla: TipoTalla): TallaUiState {
        var talla = inicializaTalla()
        talla.tallaSeleccionada[tipoTalla] = true
        return talla
    }

    private fun buscaArticuloEnPedido(articulo: ArticuloDePedidoUiState): ArticuloDePedidoUiState? {
        return pedidoUiState.articulos.find { a ->
            a.articuloId == articulo.articuloId && a.tamaño == articulo.tamaño
        }
    }

    private fun buscaPosicionArticuloPedido(articulo: ArticuloDePedidoUiState): Int {
        return pedidoUiState.articulos.indexOf(articulo)
    }

    private fun actualizarCifrasPedido() {
        numeroArticulosState = pedidoUiState.articulos.map { a -> a.cantidad }.sum()
        totalCompraState = pedidoUiState.articulos.map { a -> a.cantidad * a.precio }.sum()
    }

    suspend private fun getCliente(login: String) = clienteRepository.getClienteCorreo(login)

    private fun Articulo.toArticuloUiState() =
        ArticuloUiState(this.id, this.imagen, this.descripcion, this.precio, this.tipo, false)

    private fun iniciarNuevoPedido(): PedidoUiState {
        return PedidoUiState(
            dniCliente = clienteState.correo,
            total = 0F,
            fecha = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
            articulos = mutableListOf<ArticuloDePedidoUiState>(),
            iniciado = false
        )
    }

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
//            pedidoUiState = pedidoUiState.copy(dniCliente = c.dni)
            if (clienteState.deseados.size > 0) articulosState =
                articulosState.checkFavoritos().toMutableStateList()
        }
    }

    fun clearTienda() {
        viewModelScope.launch {
            clienteState = inicializaCliente(null)
            pedidoUiState = iniciarNuevoPedido()
            tallaUiState = inicializaTalla()
            filtroState = ""
            carritoState = false
            numeroArticulosState = 0
            totalCompraState = 0f
            estaFiltrandoState = false
            articuloSeleccionadoState = null
            articulodDePedidoUiState = ArticuloDePedidoUiState()
            mostrarFavoritoState = false
            articulosState = getArticulos()
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

    private fun MutableList<Articulo>.toArticulosUiState() =
        this.map { it.toArticuloUiState() }.toMutableList()
    /*
        private fun ArticuloDePedidoUiState.toArticuloDePedido() =
            ArticuloDePedido(this.articuloId, this.tamaño, this.cantidad)
    */
    /*
    private fun MutableList<ArticuloDePedidoUiState>.toArticuloDePedido(): MutableList<ArticuloDePedido> {
        return this.map { it.toArticuloDePedido() }.toMutableList()
    }
    */

    /*
        private fun PedidoUiState.toPedido() = Pedido(
            this.pedidoId,
            this.dniCliente,
            totalCompraState,
            this.fecha,
            this.articulos.toArticuloDePedido()
        )
        */

}
/*
fun ArticuloUiState.toArticuloDePedioUiState() =
    ArticuloDePedidoUiState(this.id, this.url, descripcion, 0, this.precio, 1)

*/
