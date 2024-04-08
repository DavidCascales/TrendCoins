package com.pmdm.tienda.utilities.validacion.validadores

import com.pmdm.tienda.utilities.validacion.Validacion
import com.pmdm.tienda.utilities.validacion.Validador
import java.util.Locale

class ValidadorDni(
    val error: String = "El DNI no es válido"
) : Validador<String> {
    override fun valida(texto: String): Validacion {
        val letras = "TRWAGMYFPDXBNJZSQVHLCKE"
        val dni = texto.uppercase(Locale.getDefault())
        var hayError = dni.length != 9 || !dni.matches(Regex("[0-9]{8}[A-Z]"))
        if (!hayError) {
            val letra = dni.substring(8, 9)
            val index = Integer.parseInt(dni.substring(0, 8)) % 23
            hayError = letras.substring(index, index + 1) != letra
        }
        return object : Validacion {
            override val hayError: Boolean
                get() = hayError
            override val mensajeError: String
                get() = error
        }
    }
}