package com.pmdm.tienda.ui.features.tienda.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.agenda.utilities.imagenes.Imagenes.Companion.base64ToAndroidBitmap
import com.pmdm.tienda.ui.composables.FilterChipWithoutIcon
import com.pmdm.tienda.ui.features.tienda.ArticuloDePedidoUiState
import com.pmdm.tienda.ui.features.tienda.ArticuloUiState
import com.pmdm.tienda.ui.features.tienda.TallaEvent
import com.pmdm.tienda.ui.features.tienda.TallaUiState
import com.pmdm.tienda.ui.features.tienda.TiendaEvent
import com.pmdm.tienda.ui.features.tienda.TipoTalla
import com.trendCoins.models.ArticuloCarrito
import kotlin.random.Random

@Composable
fun DialogoCompra(
    articulo: ArticuloUiState,
    //talla: TallaUiState,
    onClickAñadirCesta: (ArticuloUiState) -> Unit,
    onDismissRequest: () -> Unit,
    onTiendaEvent: (TiendaEvent) -> Unit
) {

    val contexto = LocalContext.current
    var tallaSeleccionadaState by remember {
        mutableStateOf(false)
    }


    AlertDialog(
        modifier = Modifier.height(500.dp),
        onDismissRequest = onDismissRequest,
        text = {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                val contexto = LocalContext.current

                Image(
                    modifier = Modifier.size(310.dp),
                    bitmap = base64ToAndroidBitmap(articulo.imagen).asImageBitmap(),
                    contentDescription = articulo.descripcion,
                    contentScale = ContentScale.Crop
                )


                Text("Elige la talla ", color = Color(0xFFFFA500))


                val sizes = listOf("S", "M", "L", "XL")
                Row {
                    sizes.forEach { size ->
                        Button(onClick = { onTiendaEvent(TiendaEvent.OnTallaChange(size)) }) {
                            Text(text = size)
                            tallaSeleccionadaState=true
                        }
                    }
                }


/*
                LazyRow(
                    contentPadding = PaddingValues(all = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                )
                {
                    items(contenido.size)
                    {
                        FilterChipWithoutIcon(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textoState = contenido[it].label,
                            seleccionadoState = contenido[it].selected,
                            onClick = {
                                tallaSeleccionadaState=!tallaSeleccionadaState
                                contenido[it].onClick()
                            }
                        )
                    }

                }

                */
            }

        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (tallaSeleccionadaState) {
                        onClickAñadirCesta(articulo)
                        onDismissRequest()

                    } else
                        Toast.makeText(
                            contexto,
                            "Debes seleccionar una talla\nantes de añadir al carrito",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            ) {
                Text("Añadir a la cesta")
            }
        },
    )


}
/*
@Preview
@Composable
fun dialogoCompraTest() {
    var tallaSeleccionada = mutableMapOf<TipoTalla, Boolean>()
    val urlBase = "@drawable/"
    val articulo =
        ArticuloUiState(1, "${urlBase}imagen$1", Random.nextFloat() * (100) + 200, "", false)
    DialogoCompra(articulo = articulo, TallaUiState(tallaSeleccionada), {}, {}, {})
}*/