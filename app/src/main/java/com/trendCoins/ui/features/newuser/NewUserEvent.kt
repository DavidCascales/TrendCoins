package com.trendCoins.ui.features.newuser

import androidx.navigation.NavOptions
import com.pmdm.tienda.ui.features.newuser.datospersonales.DatosPersonalesEvent
import com.pmdm.tienda.ui.features.newuser.direccion.DireccionEvent
import com.pmdm.tienda.ui.features.newuser.newuserpassword.NewUserPasswordEvent

sealed interface NewUserEvent {

    data class NombreChanged(val nombre:String): NewUserEvent
    data class TelefonoChanged(val telefono:String): NewUserEvent

    data class CalleChanged(val calle:String): NewUserEvent
    data class CiudadChanged(val ciudad:String): NewUserEvent

    data class LoginChanged(val login: String) : NewUserEvent
    data class PasswordChanged(val password: String) : NewUserEvent
    data class onClickCrearCliente(
        val onNavigateToLogin: ((correo: String, navOptions: NavOptions?) -> Unit)?
    ) : NewUserEvent
}