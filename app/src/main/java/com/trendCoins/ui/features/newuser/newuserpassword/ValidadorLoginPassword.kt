package com.pmdm.tienda.ui.features.newuser.newuserpassword

import com.pmdm.tienda.utilities.validacion.Validador
import com.pmdm.tienda.utilities.validacion.ValidadorCompuesto
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorCorreo
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorLongitudMinimaTexto
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorTextoNoVacio
import javax.inject.Inject

class ValidadorLoginPassword @Inject constructor() : Validador<LoginPasswordUiState> {
    val validacionLogin = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("El login no puede estar vacío"))
        .add(ValidadorCorreo("El login debe de ser un e-Mail"))
    val validacionPassword = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("El password no puede estar vacío"))
        .add(ValidadorLongitudMinimaTexto(8, "El password debe de tener al menos 8 carácteres"))

    override fun valida(newUserPasswordUiState: LoginPasswordUiState): ValidacionLoginPasswordUiState {
        val validacionLogin = validacionLogin.valida(newUserPasswordUiState.login)
        val validacionPassword = validacionPassword.valida(newUserPasswordUiState.password)

        return ValidacionLoginPasswordUiState(
            validacionLogin = validacionLogin,
            validacionPassword = validacionPassword
        )
    }
}