package com.pmdm.tienda.utilities.validacion

// Validacion.kt -----------------------------------------------
// Abstracción del resultado de una validación.
// Si hay error, se indica el mensaje de error.
// Será el UIState que reciben nuestros TextField para indicar si hay error o no.
// Puedo crear una unstancia de Validacion con un objeto anónimo que
// implemente la interfaz Validacion usando object : Validacion { ... }
interface Validacion {
    val hayError: Boolean
        get() = false
    val mensajeError: String?
        get() = null
}