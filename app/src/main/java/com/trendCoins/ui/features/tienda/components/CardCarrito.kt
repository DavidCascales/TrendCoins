package com.trendCoins.ui.features.tienda.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trendCoins.models.ArticuloCarrito
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardCarrito(
    modifier: Modifier = Modifier,
    articulo: ArticuloCarrito,
    onClickMas: () -> Unit,
    onClickMenos: () -> Unit
) {

    ElevatedCard(modifier = Modifier.wrapContentSize(), onClick = {

    }) {

        val df = DecimalFormat("#.##")

        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp),
        ) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)) {
                Text(
                    text = articulo.descripcion,
                    fontSize = 13.sp,
                    style = TextStyle(lineHeight = 15.sp)
                )
                Text(

                    text = "Talla: ${articulo.talla}",
                )
                Spacer(modifier = Modifier.height(10.dp))


                Row() {
                    Text(
                        modifier = Modifier.weight(0.5f),
                        text = "${df.format(articulo.precio)} puntos",
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedIconButton(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(25.dp)
                            .weight(0.1f),
                        onClick = onClickMas,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "uno más",
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                        )
                    }
                    Text(
                        modifier = Modifier.weight(0.1f),
                        text = "${articulo.cantidad}",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    OutlinedIconButton(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(25.dp)
                            .weight(0.1f),
                        onClick = onClickMenos,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "uno más",
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                        )
                    }
                }
            }
        }
    }
}

