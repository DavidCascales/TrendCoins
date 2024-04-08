package com.pmdm.tienda.utilities.validacion

// Validador.kt -----------------------------------------------
// Abstracción de una función de validadora de datos.
// Devuelve un objeto Validacion que devolverá un estado
// de validación para un TextField o un Snackbar.
interface Validador<T> {
    fun valida(datos: T): Validacion
}

