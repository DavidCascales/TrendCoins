package com.trendCoins.ui.features.tienda.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pmdm.agenda.utilities.imagenes.Imagenes.Companion.base64ToAndroidBitmap
import com.trendCoins.ui.features.tienda.ArticuloUiState
import com.trendCoins.ui.features.tienda.TiendaEvent

@Composable
fun DialogoCompra(
    articulo: ArticuloUiState,
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
