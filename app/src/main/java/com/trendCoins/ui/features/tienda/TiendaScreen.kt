package com.trendCoins.ui.features.tienda

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.tienda.ui.features.tienda.RuletaScreen
import com.trendCoins.ui.features.tienda.components.BarraNavegacion
import com.trendCoins.ui.features.tienda.components.Escaparate
import com.trendCoins.ui.features.tienda.components.BarraSuperior
import com.trendCoins.ui.features.tienda.components.Carrito
import com.trendCoins.R
import com.trendCoins.models.ArticuloCarrito
import com.trendCoins.models.Cliente
import com.trendCoins.ui.features.tienda.components.DeseadosScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TiendaScreen(
    clienteUiState: Cliente,
    articulos: List<ArticuloUiState>,
    articuloSeleccionado: ArticuloUiState?,
    filtro: String,
    estaFiltrando: Boolean,
    totalCompra: Int,
    onTiendaEvent: (TiendaEvent) -> Unit,
    onNavigateToNewUser: (String) -> Unit,
    onNavigateToLogin: () -> Unit,
    screenState: Int,
    onScreenChange: (Int) -> Unit,
    listaRuleta: List<String>,
    puntosRuleta: Int,
    verResultadoRuleta: Boolean,
    deseados: List<ArticuloUiState>,
    listaArticuloCarrito: List<ArticuloCarrito>,
    mostrarCarrito: Boolean
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var salirState by remember {
        mutableStateOf(true)
    }
    val contexto = LocalContext.current
    Scaffold(
        topBar = {
            BarraSuperior(
                clienteUiState = clienteUiState,
                onClickUsuario = { opcion ->
                    if (opcion == 0)  {
                        onNavigateToNewUser(clienteUiState.correo)
                        onTiendaEvent(TiendaEvent.OnClickSalir)
                    }

                    else {
                        onNavigateToLogin()
                        onTiendaEvent(TiendaEvent.OnClickSalir)
                    }
                },
                puntosClicker = clienteUiState.puntos,
            )


        },
        bottomBar = {
            BarraNavegacion(
                onScreenChange = onScreenChange
            )
        },
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(it)
        )
        {
            when (screenState) {
                0 -> {
                    Escaparate(
                        articulos = articulos,
                        articuloSeleccionado = articuloSeleccionado,
                        onTiendaEvent = onTiendaEvent,
                        filtro = filtro,
                        estaFiltrando = estaFiltrando,
                        onEstaFiltrandoChange = { onTiendaEvent(TiendaEvent.OnClickEstaFiltrando(it)) },
                        onFiltroChange = { onTiendaEvent(TiendaEvent.OnFiltroChange(it)) },
                        onClickFiltrar = { onTiendaEvent(TiendaEvent.OnClickFiltrar(it)) },
                        onClickQuitarFiltro = { onTiendaEvent(TiendaEvent.OnClickQuitarFiltro) }
                    )
                }

                1 -> {

                    DeseadosScreen(
                        deseados = deseados,
                        articuloSeleccionado = articuloSeleccionado,
                        onTiendaEvent = onTiendaEvent
                    )


                }

                2 -> {

                    RuletaScreen(

                        onTiendaEvent = onTiendaEvent,
                        listaRuleta = listaRuleta,
                        puntosRuleta = puntosRuleta,
                        verResultadoRuleta = verResultadoRuleta
                    )
                }

                3 -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.padding(20.dp))
                            Text(
                                text = "Clicker Coins",
                                fontWeight = FontWeight.Bold,
                                fontSize = 60.sp,
                                color = Color(0xFFFFA500)
                            )

                        }

                        Spacer(modifier = Modifier.padding(40.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(painterResource(id = R.drawable.imgclicker),
                                contentDescription = "Imagen clicker",
                                modifier = Modifier
                                    .rotate(20f)
                                    .clickable { onTiendaEvent(TiendaEvent.OnClickSumaPuntosClicker) })
                        }
                    }
                }

                4 -> {
                    if (!mostrarCarrito)
                    {
                        Column (modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center){
                            Text(text = "No tienes articulos en el carrito",
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                color = Color.Red
                            )
                        }
                    }
                    else
                    {
                        Carrito(
                            listaArticuloCarrito = listaArticuloCarrito,
                            onTiendaEvent = onTiendaEvent,
                            totalCompra = totalCompra,
                            puntos=clienteUiState.puntos
                        )
                    }

                }
            }

            /* if (!carrito) {
                 Escaparate(
                     articulos = articulos,
                     articuloSeleccionado = articuloSeleccionado,
                     tallaUiState = tallaUiState,
                     onTallaEvent = onTallaEvent,
                     onTiendaEvent = onTiendaEvent
                 )
             } else Carrito(pedido = pedido, onTiendaEvent = onTiendaEvent)*/
        }

    }
}