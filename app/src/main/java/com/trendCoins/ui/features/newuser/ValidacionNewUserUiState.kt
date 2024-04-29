package com.pmdm.tienda.ui.features.newuser

import com.pmdm.tienda.ui.features.newuser.datospersonales.ValidacionDatosPersonalesUiState
import com.pmdm.tienda.ui.features.newuser.direccion.ValidacionDireccionUiState
import com.pmdm.tienda.ui.features.newuser.newuserpassword.ValidacionLoginPasswordUiState
import com.pmdm.tienda.utilities.validacion.Validacion
import com.pmdm.tienda.utilities.validacion.ValidacionCompuesta

data class ValidacionNewUserUiState(


    val validacionLogin: Validacion = object : Validacion {},
    val validacionPassword: Validacion = object : Validacion {},
    val validacionNombre: Validacion = object : Validacion {},
    val validacionTelefono: Validacion = object : Validacion {},
    val validacionCalle: Validacion = object : Validacion {},
    val validacionCiudad: Validacion = object : Validacion {},


) : Validacion {
    private lateinit var validacionCompuesta : ValidacionCompuesta

    private fun componerValidacion(): ValidacionCompuesta {
        validacionCompuesta = ValidacionCompuesta()
            .add(validacionLogin)
            .add(validacionPassword)
            .add(validacionNombre)
            .add(validacionTelefono)
            .add(validacionCalle)
            .add(validacionCiudad)
        return validacionCompuesta
    }

    override val hayError: Boolean
        get() = componerValidacion().hayError
    override val mensajeError: String?
        get() = validacionCompuesta.mensajeError
}
