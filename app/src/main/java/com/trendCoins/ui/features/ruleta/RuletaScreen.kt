package com.pmdm.tienda.ui.features.tienda

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.commandiron.spin_wheel_compose.SpinWheel
import com.commandiron.spin_wheel_compose.state.rememberSpinWheelState
import com.pmdm.tienda.ui.features.tienda.components.BarraNavegacion
import com.pmdm.tienda.ui.features.tienda.components.Escaparate
import com.pmdm.tienda.ui.features.tienda.components.BarraSuperiorBuena
import com.trendCoins.models.Cliente
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RuletaScreen(
    mostrarResultado:Boolean,
    resultadoFinalRuleta:(Int)->Unit,
    onObtenerResultadoRuleta:(Int)->Int,
    listaRuleta:List<String>
) {
//    val textList by remember {
//        mutableStateOf(
//            listOf(
//                "Pie 1",
//                "Pie 2",
//                "Pie 3",
//                "Pie 4",
//                "Pie 5",
//                "Pie 6",
//                "Pie 7",
//                "Pie 8"
//            )
//        )
//    }

    var resultado by remember {
        mutableStateOf(0)
    }

    val state = rememberSpinWheelState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        SpinWheel(
            state = state,
            onClick = {
                scope.launch {
                    state.animate { pieIndex ->
                        resultadoFinalRuleta(pieIndex)
                        resultado=onObtenerResultadoRuleta(pieIndex)
                    }
                }
            }
        ) { pieIndex ->
            Text(text = listaRuleta[pieIndex])
            //resultado = textList[pieIndex]
        }
        if (mostrarResultado)
        {

            Text(text = resultado.toString())


        }
//                        Button(onClick = { p++ }, modifier = Modifier.width(10.dp)) {
//
//                        }
    }
}