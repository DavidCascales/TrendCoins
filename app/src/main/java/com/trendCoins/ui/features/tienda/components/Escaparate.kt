package com.pmdm.tienda.ui.features.tienda.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.tienda.ui.features.tienda.ArticuloUiState
import com.pmdm.tienda.ui.features.tienda.TallaEvent
import com.pmdm.tienda.ui.features.tienda.TallaUiState
import com.pmdm.tienda.ui.features.tienda.TiendaEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Escaparate(
    articulos: List<ArticuloUiState>,
    articuloSeleccionado: ArticuloUiState?,
    tallaUiState: TallaUiState,
    onTiendaEvent: (TiendaEvent) -> Unit,
    filtro: String,
    estaFiltrando: Boolean,
    onEstaFiltrandoChange: (Boolean) -> Unit,
    onFiltroChange: (String) -> Unit,
    onClickFiltrar: (String) -> Unit,
    onClickQuitarFiltro: () -> Unit,
) {

   Column() {

       if (!estaFiltrando) {
           IconButton(onClick = { onEstaFiltrandoChange(!estaFiltrando) }) {
               Icon(
                   imageVector = Icons.Filled.Search,
                   contentDescription = "Filtrar"
               )
           }
       } else {
           SearchBar(
               modifier = Modifier
                   .fillMaxWidth().height(70.dp),
               query = filtro,
               placeholder = {
                   Text(text = "Filtrar")
               },
               onQueryChange = {
                   onFiltroChange(it)
                   onClickFiltrar(filtro)
               },
               onSearch = { onClickFiltrar(filtro) },
               active = estaFiltrando,
               onActiveChange = onEstaFiltrandoChange,
               leadingIcon = {
                   Icon(
                       modifier = Modifier.clickable {
                           onFiltroChange("")
                       },
                       imageVector = Icons.Filled.Search,
                       contentDescription = "Filtrar",
                   )
               },
               trailingIcon = {
                   Icon(
                       modifier = Modifier.clickable {
                           onEstaFiltrandoChange(!estaFiltrando)
                           onClickQuitarFiltro()
                       },
                       imageVector = Icons.Filled.Close,
                       contentDescription = "Filtrar",
                   )
               },
           )
           {
           }
       }

       Spacer(modifier = Modifier.padding(20.dp))
       LazyVerticalGrid(
           columns = GridCells.Adaptive(150.dp),
           contentPadding = PaddingValues(all = 10.dp),
           horizontalArrangement = Arrangement.spacedBy(10.dp),
           verticalArrangement = Arrangement.spacedBy(10.dp)
       ) {
           items(items = articulos,
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
               //talla = tallaUiState,
               onDismissRequest = {
                   onTiendaEvent(TiendaEvent.OnDismissDialog)
               },
               onTiendaEvent = onTiendaEvent
           )
       }
   }

}