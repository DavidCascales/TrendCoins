package com.pmdm.tienda.ui.features.tienda

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Spin Coins", fontSize = 50.sp, fontWeight = FontWeight.Bold, style = TextStyle(
                brush = Brush.linearGradient(colors = listOf(  Color(0xFFef476f),
                    Color(0xFFf78c6b),
                    Color(0xFFffd166),
                    Color(0xFF83d483),
                    Color(0xFF06d6a0),
                    Color(0xFF0cb0a9),
                    Color(0xFF118ab2),
                    Color(0xFF073b4c)))
            ))
        }

        Spacer(modifier = Modifier.padding(30.dp))
        Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
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
            Spacer(modifier = Modifier.padding(30.dp))
            if (verResultadoRuleta) {
                Text(text = "Has obtenido un total de $puntosRuleta puntos", color = Color.Green)
            }
        }

    }
}