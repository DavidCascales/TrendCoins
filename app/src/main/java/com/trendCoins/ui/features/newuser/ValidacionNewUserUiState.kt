package com.pmdm.tienda.ui.features.newuser

import com.pmdm.tienda.ui.features.newuser.datospersonales.ValidacionDatosPersonalesUiState
import com.pmdm.tienda.ui.features.newuser.direccion.ValidacionDireccionUiState
import com.pmdm.tienda.ui.features.newuser.newuserpassword.ValidacionLoginPasswordUiState
import com.pmdm.tienda.utilities.validacion.Validacion
import com.pmdm.tienda.utilities.validacion.ValidacionCompuesta

data class ValidacionNewUserUiState(
    val validacionDatosPersonalesUiState: ValidacionDatosPersonalesUiState = ValidacionDatosPersonalesUiState(),
    val validacionDireccionUiState: ValidacionDireccionUiState = ValidacionDireccionUiState(),
    val validacionLoginPasswordUiState: ValidacionLoginPasswordUiState = ValidacionLoginPasswordUiState()
) : Validacion {
    private lateinit var validacionCompuesta : ValidacionCompuesta

    private fun componerValidacion(): ValidacionCompuesta {
        validacionCompuesta = ValidacionCompuesta()
            .add(validacionDatosPersonalesUiState)
            .add(validacionDireccionUiState)
            .add(validacionLoginPasswordUiState)
        return validacionCompuesta
    }

    override val hayError: Boolean
        get() = componerValidacion().hayError
    override val mensajeError: String?
        get() = validacionCompuesta.mensajeError
}
