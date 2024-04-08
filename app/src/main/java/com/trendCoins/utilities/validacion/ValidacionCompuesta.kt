package com.pmdm.tienda.utilities.validacion

// ValidacionCompuesta.kt -----------------------------------------------
// Es una clase de utilidad que tiene una lista de validaciones que debemos pasar antes
// de dar por válidos los datos de un formulario.
// Rehusamos para ello los objetos que representan los estados de validación de cada campo y si alguno
// de ellos tiene error lo indicaremos en la propiedad calculada de solo lectura hayError de esta clase.
open class ValidacionCompuesta : Validacion {
    private val validaciones = mutableListOf<Validacion>()
    fun add(validacion: Validacion): ValidacionCompuesta {
        validaciones.add(validacion)
        return this
    }
    override val hayError: Boolean
        get() = validaciones.any { it.hayError }

    override val mensajeError: String?
        get() = validaciones.firstOrNull { it.hayError }?.mensajeError
}