package com.pmdm.tienda.utilities.validacion.validadores

import android.util.Range
import com.pmdm.tienda.utilities.validacion.Validacion
import com.pmdm.tienda.utilities.validacion.Validador

class ValidadorNumeroReal(
    val rango: Range<Double> = Range(0.0, Double.MAX_VALUE),
    val error: String = "Se espera un valor real"
) : Validador<String> {
    override fun valida(texto: String): Validacion {
        return object : Validacion {
            override val hayError: Boolean
                get() = !texto.matches(Regex("^[0-9]+(\\.[0-9]+)?$"))
                        ||
                        !rango.contains(texto.toDouble())
            override val mensajeError: String
                get() = error
        }
    }
}