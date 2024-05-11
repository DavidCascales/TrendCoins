package com.pmdm.tienda.ui.features.tienda.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Search

import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.substring
import com.pmdm.agenda.utilities.imagenes.Imagenes
import com.trendCoins.models.Cliente
import com.trendCoins.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(
    filtro: String,
    estaFiltrando: Boolean,
    totalCompra: Float,
    carrito: Boolean,
    clienteUiState: Cliente,
    onEstaFiltrandoChange: (Boolean) -> Unit,
    onFiltroChange: (String) -> Unit,
    onClickFiltrar: (String) -> Unit,
    onClickUsuario: (Int) -> Unit,
    onClickCompar: () -> Unit,
    onClickCasa: () -> Unit,
) {
    val opcionesMenuUsuario = listOf(
        "Editar datos",
        "Cerrar sesión"
    )

    var mostrarMenu by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {},
        actions = {

            if (carrito) {
                val df = DecimalFormat("#.##")
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(0.5f)
                ) {
                    Text(
                        modifier = Modifier.weight(0.3f),
                        text = "Total: ${df.format(totalCompra)}€"
                    )
                    IconButton(
                        modifier = Modifier.weight(0.2f),
                        onClick = { onEstaFiltrandoChange(true) })
                    {
                        OutlinedButton(onClick = onClickCompar) {
                            Text(text = "Comprar")
                        }
                    }
                }
            } else if (!estaFiltrando) {
                IconButton(onClick = { onEstaFiltrandoChange(!estaFiltrando) }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Filtrar"
                    )
                }
            } else {
                SearchBar(
                    modifier = Modifier
                        .weight(0.9f)
                        .size(height = 65.dp, width = 0.dp),
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
                                onClickCasa()
                            },
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Filtrar",
                        )
                    },
                )
                {
                }
            }

            IconButton(onClick = { mostrarMenu = true })
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Icon(
                        modifier = Modifier
                            .size(25.dp),
                        imageVector = Icons.Filled.Person2,
                        contentDescription = "Datos Personales",
                    )
                    val longitud = clienteUiState.nombre.length
                    var salidaNombre = clienteUiState.nombre
                    if (longitud > 2) salidaNombre = clienteUiState.nombre.substring(0, 3)
                    Text(
                        text = salidaNombre,
                        style = MaterialTheme.typography.labelSmall
                    )
                    DropdownMenu(
                        expanded = mostrarMenu,
                        onDismissRequest = { mostrarMenu = false }) {
                        for (o in opcionesMenuUsuario.indices) {
                            DropdownMenuItem(
                                text = { Text(opcionesMenuUsuario[o]) },
                                onClick = {
                                    onClickUsuario(o)
                                    mostrarMenu = false
                                })
                        }
                    }
                }
            }

        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperiorBuena(
    clienteUiState: Cliente,
    onClickUsuario: (Int) -> Unit,
    puntosClicker: Int
) {
    val opcionesMenuUsuario = listOf(
        "Editar datos",
        "Cerrar sesión"
    )

    var mostrarMenu by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Trend Coins",
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { mostrarMenu = true })
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Image(
                        modifier = Modifier
                            .size(25.dp),
                        painter = if (clienteUiState.imagen == null || clienteUiState.imagen == "") rememberVectorPainter(
                            image = Icons.Filled.Person
                        ) else
                            BitmapPainter(Imagenes.base64ToBitmap(clienteUiState.imagen)),
                        contentDescription = clienteUiState.nombre,
                        contentScale = ContentScale.Crop
                    )
                    DropdownMenu(
                        expanded = mostrarMenu,
                        onDismissRequest = { mostrarMenu = false }) {
                        for (o in opcionesMenuUsuario.indices) {
                            DropdownMenuItem(
                                text = { Text(opcionesMenuUsuario[o]) },
                                onClick = {
                                    onClickUsuario(o)
                                    mostrarMenu = false
                                })
                        }
                    }
                }
            }
        },
        actions = {

            Row(
                modifier = Modifier
                    .border(
                        border = BorderStroke(2.dp, Color(0xFFFFA500)),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clip(shape = RoundedCornerShape(4.dp))
                    .padding(PaddingValues(horizontal = 8.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.puntos),
                    contentDescription = "Puntos"
                )

                Text(text = puntosClicker.toString())

            }
            /*
      if (carrito) {
          val df = DecimalFormat("#.##")
          Row(
              modifier = Modifier
                  .padding(5.dp)
                  .weight(0.5f)
          ) {
              Text(
                  modifier = Modifier.weight(0.3f),
                  text = "Total: ${df.format(totalCompra)}€"
              )
              IconButton(
                  modifier = Modifier.weight(0.2f),
                  onClick = { onEstaFiltrandoChange(true) })
              {
                  OutlinedButton(onClick = onClickCompar) {
                      Text(text = "Comprar")
                  }
              }
          }
      } else if (!estaFiltrando) {
          IconButton(onClick = { onEstaFiltrandoChange(!estaFiltrando) }) {
              Icon(
                  imageVector = Icons.Filled.Search,
                  contentDescription = "Filtrar"
              )
          }
      } else {
          SearchBar(
              modifier = Modifier
                  .weight(0.9f)
                  .size(height = 65.dp, width = 0.dp),
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
                          onClickCasa()
                      },
                      imageVector = Icons.Filled.Close,
                      contentDescription = "Filtrar",
                  )
              },
          )
          {
          }
      }*/


        },
    )
}
/*

@Preview
@Composable
fun BarraSuperiorTest() {
    //val direccionUiState = Direccion("C\\ Las partidas", "Alicante", "03001")
    val clienteUiState = Cliente(
        "21231234Y",
        "ana@gmail.com",
        "Ana García",
        "627541665",
        direccionUiState,
        emptyList<Int>().toMutableList()
    )
    LoginTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            BarraSuperior("", true, 126f, false, clienteUiState, {}, {}, {}, {}, {}, {})
        }
    }
}*/