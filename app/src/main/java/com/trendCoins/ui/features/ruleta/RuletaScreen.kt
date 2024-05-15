package com.pmdm.tienda.ui.features.tienda

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.commandiron.spin_wheel_compose.SpinWheel
import com.commandiron.spin_wheel_compose.state.rememberSpinWheelState
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun RuletaScreen(
    onTiendaEvent: (TiendaEvent) -> Unit,
    listaRuleta: List<String>,
    puntosRuleta: Int,
    verResultadoRuleta: Boolean
) {

    val resultadosRuleta by remember {
        mutableStateOf(
            listOf(
                "10",
                "5",
                "1",
                "20",
                "100",
                "33",
                "5",
                "0"
            )
        )
    }





    val state = rememberSpinWheelState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        SpinWheel(
            state = state,
            onClick = {

                onTiendaEvent(TiendaEvent.OnchangeResultadoRuleta)

                scope.launch {
                    state.animate { pieIndex ->

                        onTiendaEvent(TiendaEvent.onClickPuntosRuleta(pieIndex))
                    }
                }
            }
        ) { pieIndex ->
            Text(text = listaRuleta[pieIndex])

        }
        if (verResultadoRuleta) {
            Text(text = puntosRuleta.toString())

        }
    }
}