package com.pmdm.tienda.ui.features.newuser.direccion

import com.pmdm.tienda.utilities.validacion.Validacion
import com.pmdm.tienda.utilities.validacion.ValidacionCompuesta

data class ValidacionDireccionUiState(
    val validacionCalle: Validacion = object : Validacion {},
    val validacionCiudad: Validacion = object : Validacion {},
    val validacionCodigoPostal: Validacion = object : Validacion {}
) : Validacion {
    private lateinit var validacionCompuesta : ValidacionCompuesta

    private fun componerValidacion(): ValidacionCompuesta {
        validacionCompuesta = ValidacionCompuesta()
            .add(validacionCalle)
            .add(validacionCiudad)
            .add(validacionCodigoPostal)
        return validacionCompuesta
    }
    override val hayError: Boolean
        get() = componerValidacion().hayError
    override val mensajeError: String?
        get() = validacionCompuesta.mensajeError
}