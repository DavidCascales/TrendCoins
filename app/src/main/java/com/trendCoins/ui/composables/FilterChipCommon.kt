package com.trendCoins.ui.composables


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipWithIcon(
    modifier: Modifier = Modifier,
    seleccionadoState: Boolean = true,
    textoState: String = "Etiqueta",
    iconState: ImageVector? = null,
    onClick: () -> Unit = {}
) {
    FilterChip(
        modifier = modifier.then(Modifier.height(FilterChipDefaults.Height)),
        selected = seleccionadoState,
        onClick = onClick,
        label = { Text(textoState) },
        leadingIcon = {
            if (seleccionadoState) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Icono seleccionado",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            } else {
                iconState?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "Icono asociado a la etiqueta",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipWithoutIcon(
    modifier: Modifier = Modifier,
    seleccionadoState: Boolean,
    textoState: String = "Etiqueta",
    onClick: () -> Unit = {}
) {
    FilterChip(
        modifier = modifier.then(Modifier.height(FilterChipDefaults.Height)),
        selected = seleccionadoState,
        onClick = onClick,
        label = { Text(textoState, textAlign= TextAlign.Center) }
    )
}

@Preview(
    showBackground = true,
)
@Composable
fun FilterChipTest() {
    var selected by remember { mutableStateOf(false) }
    var selected2 by remember { mutableStateOf(false) }
    Column()
    {
        FilterChipWithIcon(
            seleccionadoState = selected,
            textoState = "Descripción",
            iconState = Icons.Filled.Home,
            onClick = { selected = !selected }
        )
        FilterChipWithoutIcon(
            seleccionadoState = selected2,
            textoState = "XL",
            onClick = { selected2 = !selected2 }
        )
    }
}