package com.pmdm.tienda.ui.features.newuser.datospersonales

import com.pmdm.tienda.utilities.validacion.Validacion
import com.pmdm.tienda.utilities.validacion.Validador
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorDni
import com.pmdm.tienda.utilities.validacion.ValidadorCompuesto
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorLongitudMaximaTexto
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorLongitudMinimaTexto
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorTelefono
import com.pmdm.tienda.utilities.validacion.validadores.ValidadorTextoNoVacio
import javax.inject.Inject

class ValidadorDatosPersonales  @Inject constructor() : Validador<DatosPersonalesUiState> {
    val validadorDni = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("El DNI no puede estar vacío"))
        .add(ValidadorDni("El DNI no es válido"))
    val validadorNombre = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("No nombre puede estar vacío"))
        .add(ValidadorLongitudMaximaTexto(20))
    val validadorTelefono = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("No teléfono puede estar vacío"))
        .add(ValidadorTelefono("El teléfono no es válido"))

    override fun valida(datos: DatosPersonalesUiState): ValidacionDatosPersonalesUiState {
        val validacionNombre = validadorNombre.valida(datos.nombre)
        val validacionDni = validadorDni.valida(datos.dni)
        val validacionTelefono = validadorTelefono.valida(datos.telefono)

        return ValidacionDatosPersonalesUiState(
            validacionNombre = validacionNombre,
            validacionDni = validacionDni,
            validacionTelefono = validacionTelefono
        )
    }

}