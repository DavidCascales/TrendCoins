package com.trendCoins.ui.features.tienda.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import com.pmdm.agenda.utilities.imagenes.Imagenes.Companion.base64ToAndroidBitmap
import com.trendCoins.ui.features.tienda.ArticuloUiState
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardArticulo(
    modifier: Modifier = Modifier,
    articulo: ArticuloUiState,
    favorito: Boolean,
    onClickFavorito: (Boolean) -> Unit,
    onClickArticulo: (ArticuloUiState) -> Unit
) {

    ElevatedCard(modifier = Modifier.wrapContentSize(),
        onClick = { onClickArticulo(articulo) }) {
        val contexto = LocalContext.current
        val df = DecimalFormat("#.##")

        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageResource =
                contexto.resources.getIdentifier("@drawable/${articulo.imagen}", null, contexto.packageName)

            Image(
                modifier = modifier,
                bitmap = base64ToAndroidBitmap(articulo.imagen).asImageBitmap(),
                contentDescription = articulo.descripcion,
                contentScale = ContentScale.Crop
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(text = "${articulo.descripcion}")
                    Text(
                        text = "${articulo.precio} puntos",
                    )
                }

                Image(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clickable {
                            onClickFavorito(!favorito)
                        }, painter = rememberVectorPainter(
                        image = if (articulo.favorito
                        ) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder
                    ), contentDescription = "Favorito"
                )
            }
        }
    }
}