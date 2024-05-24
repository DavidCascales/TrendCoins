package com.trendCoins.ui.features.newuser


import com.pmdm.tienda.utilities.validacion.Validador
import com.pmdm.tienda.utilities.validacion.ValidadorCompuesto
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorCorreo
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorLongitudMaximaTexto
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorLongitudMinimaTexto
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorTelefono
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorTextoNoVacio
import javax.inject.Inject

class ValidadorNewUser @Inject constructor(


) : Validador<NewUserUiState> {

    val validacionLogin = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("El login no puede estar vacío"))
        .add(ValidadorCorreo("El login debe de ser un e-Mail"))
    val validacionPassword = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("El password no puede estar vacío"))
        .add(ValidadorLongitudMinimaTexto(8, "El password debe de tener al menos 8 carácteres"))
    val validadorNombre = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("No nombre puede estar vacío"))
        .add(ValidadorLongitudMaximaTexto(20))
    val validadorTelefono = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("No teléfono puede estar vacío"))
        .add(ValidadorTelefono("El teléfono no es válido"))
    val validadorCalle = ValidadorTextoNoVacio("La calle no puede estar vacía")
    val validadorCiudad = ValidadorTextoNoVacio("La ciudad no puede estar vacía")

    override fun valida(datos: NewUserUiState): ValidacionNewUserUiState {

        val validacionNombre = validadorNombre.valida(datos.nombre)
        val validacionTelefono = validadorTelefono.valida(datos.telefono)
        val validacionCalle = validadorCalle.valida(datos.calle)
        val validacionCiudad = validadorCiudad.valida(datos.ciudad)
        val validacionLogin = validacionLogin.valida(datos.correo)
        val validacionPassword = validacionPassword.valida(datos.contraseña)


        return ValidacionNewUserUiState(
            validacionLogin=validacionLogin, validacionPassword=validacionPassword,
            validacionNombre=validacionNombre, validacionTelefono=validacionTelefono,
            validacionCalle=validacionCalle, validacionCiudad=validacionCiudad
        )
    }
}
