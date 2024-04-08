package com.pmdm.tienda.utilities.validacion

// ValidadorCompuesto.kt -----------------------------------------------
// Implementa la interfaz Validador y es una clase de utilidad que tiene
// una lista de validadores que debemos pasar para un conjunto de datos.
// Por ejemplo para un teléfono, podemos tener una validación que compruebe
// que no está vacío, otra que compruebe que tiene una longitud mínima.
// Nota: Las validaciones se ejecutan en orden y si alguna de ellas tiene error
// se devuelve el error y no se ejecutan las siguientes.
open class ValidadorCompuesto<T> : Validador<T> {
    private val validadores = mutableListOf<Validador<T>>()

    fun add(validador: Validador<T>): ValidadorCompuesto<T> {
        validadores.add(validador)
        return this
    }

    override fun valida(datos: T): Validacion =
        validadores
            .map { it.valida(datos) }
            .firstOrNull { it.hayError }
            ?: object : Validacion {}
}
