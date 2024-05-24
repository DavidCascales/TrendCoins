package com.trendCoins.ui.features.tienda.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.trendCoins.ui.features.tienda.ArticuloUiState
import com.trendCoins.ui.features.tienda.TiendaEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun DeseadosScreen(
    deseados: List<ArticuloUiState>,
    articuloSeleccionado: ArticuloUiState?,
    onTiendaEvent: (TiendaEvent) -> Unit
) {

    Column {

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
                 ) {
            Text(text = "Aqui van tus articulos deseados", fontWeight = FontWeight.Bold, color = Color.Yellow)
        }
        Spacer(modifier = Modifier.padding(20.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(all = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items = deseados,
                key = { it.id }) { item ->
                var favorito by remember {
                    mutableStateOf(item.favorito)
                }
                CardArticulo(
                    articulo = item,
                    favorito = favorito,
                    modifier = Modifier.size(150.dp),
                    onClickFavorito = {
                        onTiendaEvent(TiendaEvent.OnClickFavorito(item))
                        favorito = it
                    },
                    onClickArticulo = {
                        onTiendaEvent(TiendaEvent.OnClickArticulo(it))
                    }
                )
            }
        }
        if (articuloSeleccionado != null) {
            DialogoCompra(
                articulo = articuloSeleccionado,
                onClickAñadirCesta = {

                    onTiendaEvent(TiendaEvent.OnClickAñadirCesta(it))
                },

                onDismissRequest = {
                    onTiendaEvent(TiendaEvent.OnDismissDialog)
                },
                onTiendaEvent = onTiendaEvent
            )
        }
    }
}
