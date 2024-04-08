package com.pmdm.tienda.utilities.validacion.validadores

import android.util.Range
import com.pmdm.tienda.utilities.validacion.Validacion
import com.pmdm.tienda.utilities.validacion.Validador

class ValidadorNumeroEntero(
    val rango: Range<Int> = Range(0, Int.MAX_VALUE),
    val error: String = "Se espera un valor entero"
) : Validador<String> {
    override fun valida(texto: String): Validacion {
        return object : Validacion {
            override val hayError: Boolean
                get() = !texto.matches(Regex("^[+-]?[0-9]+$"))
                        ||
                        !rango.contains(texto.toInt())
            override val mensajeError: String
                get() = error
        }
    }
}