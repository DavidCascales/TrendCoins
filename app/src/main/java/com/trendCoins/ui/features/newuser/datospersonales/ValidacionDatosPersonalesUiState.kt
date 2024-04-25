package com.pmdm.tienda.ui.features.newuser.datospersonales

import com.pmdm.tienda.utilities.validacion.Validacion
import com.pmdm.tienda.utilities.validacion.ValidacionCompuesta


data class ValidacionDatosPersonalesUiState(
    val validacionNombre: Validacion = object : Validacion {},
    val validacionDni: Validacion = object : Validacion {},
    val validacionTelefono: Validacion = object : Validacion {},
) : Validacion {
    private lateinit var validacionCompuesta : ValidacionCompuesta

    private fun componerValidacion(): ValidacionCompuesta {
        validacionCompuesta = ValidacionCompuesta()
            .add(validacionNombre)
            .add(validacionDni)
            .add(validacionTelefono)
        return validacionCompuesta
    }
    override val hayError: Boolean
        get() = componerValidacion().hayError
    override val mensajeError: String?
        get() = validacionCompuesta.mensajeError
}
