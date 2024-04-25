package com.pmdm.tienda.ui.features.newuser

import com.pmdm.tienda.ui.features.newuser.datospersonales.ValidadorDatosPersonales
import com.pmdm.tienda.ui.features.newuser.direccion.ValidadorDireccion
import com.pmdm.tienda.ui.features.newuser.newuserpassword.ValidadorLoginPassword
import com.pmdm.tienda.utilities.validacion.Validador
import javax.inject.Inject

class ValidadorNewUser @Inject constructor(
    val validadorDatosPersonales: ValidadorDatosPersonales,
    val validadorDireccion: ValidadorDireccion,
    val validadorLoginPassword: ValidadorLoginPassword
) : Validador<NewUserUiState> {
    override fun valida(datos: NewUserUiState): ValidacionNewUserUiState {
        val validacionDatosPersonales = validadorDatosPersonales.valida(datos.datosPersonalesUiState)
        val validacionDireccion = validadorDireccion.valida(datos.direccionUiState)
        val validacionLoginPassword = validadorLoginPassword.valida(datos.newUserPasswordUiState)

        return ValidacionNewUserUiState(
            validacionDatosPersonalesUiState = validacionDatosPersonales,
            validacionDireccionUiState = validacionDireccion,
            validacionLoginPasswordUiState = validacionLoginPassword
        )
    }
}
