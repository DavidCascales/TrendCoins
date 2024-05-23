package com.pmdm.tienda.ui.features.tienda.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pmdm.tienda.ui.features.tienda.TiendaEvent
import com.trendCoins.models.ArticuloCarrito


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carrito(
    listaArticuloCarrito: List<ArticuloCarrito>,
    onTiendaEvent: (TiendaEvent) -> Unit,
    totalCompra: Int,
    puntos: Int
) {
    var dialogoCompra by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(all = 10.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(listaArticuloCarrito) { item ->
                    CardCarrito(
                        articulo = item,
                        modifier = Modifier.size(80.dp),
                        onClickMas = {
                            onTiendaEvent(TiendaEvent.OnClickMas(item))
                        },
                        onClickMenos = {
                            onTiendaEvent(TiendaEvent.OnClickMenos(item))
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End) {
            Button(onClick = {
                dialogoCompra = true
            }) {
                Text(text = "Total: $totalCompra")
            }
        }


        if (dialogoCompra) {
            AlertDialog(onDismissRequest = { dialogoCompra = false }, text = {
                if (puntos >= totalCompra) {
                    Text(text = "Compra realizada con exito")
                } else {
                    Text(text = "No tienes suficientes puntos")
                }
            }, confirmButton = {
                Button(onClick = {
                    if (puntos >= totalCompra) {
                        onTiendaEvent(TiendaEvent.OnCompraRealizada)
                    }
                    dialogoCompra = false
                }) {
                    Text(text = "Aceptar")
                }
            })

        }
    }

}

