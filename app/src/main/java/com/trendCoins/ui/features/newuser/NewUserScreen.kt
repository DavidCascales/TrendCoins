package com.trendCoins.ui.features.newuser

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import com.pmdm.agenda.utilities.imagenes.Imagenes
import com.trendCoins.ui.composables.OutlinedTextFieldCalle
import com.trendCoins.ui.composables.OutlinedTextFieldCiudad
import com.trendCoins.ui.composables.OutlinedTextFieldEmail
import com.trendCoins.ui.composables.OutlinedTextFieldName
import com.trendCoins.ui.composables.OutlinedTextFieldPassword
import com.trendCoins.ui.composables.OutlinedTextFieldPhone
import com.trendCoins.utilities.RegistroSelectorDeImagenesConGetContent


@Composable
fun NewUserScreenBuena(
    newUserUiState: NewUserUiState,
    validacionNewUserUiState: ValidacionNewUserUiState,
    esNuevoClienteState:Boolean,
    mostrarSnack: Boolean,
    mensaje: String,
    onNewUserEvent: (NewUserEvent)-> Unit,
    onNavigateToLogin: ((correo: String, navOptions: NavOptions?) -> Unit)?,
    onFotoCambiada :(ImageBitmap)->Unit
)
{
    val selectorDeImagenes=RegistroSelectorDeImagenesConGetContent(onFotoCambiada)
    Column (horizontalAlignment = CenterHorizontally){
        Spacer(modifier = Modifier.padding(10.dp) )
        Image(modifier= Modifier
            .size(200.dp)
            .clickable {
                selectorDeImagenes.launch("image/*")
            },painter = if (newUserUiState.imagen == null || newUserUiState.imagen == "") rememberVectorPainter(image = Icons.Filled.Person) else
            BitmapPainter(Imagenes.base64ToBitmap(newUserUiState.imagen)),
            contentDescription = newUserUiState.nombre,
            contentScale = ContentScale.Crop)

        Spacer(modifier = Modifier.padding(10.dp) )
        OutlinedTextFieldName(nombreState = newUserUiState.nombre,
            validacionState = validacionNewUserUiState.validacionNombre,
            onValueChange = {onNewUserEvent(NewUserEvent.NombreChanged(it))})
        Spacer(modifier = Modifier.padding(5.dp) )
        OutlinedTextFieldPhone(telefonoState = newUserUiState.telefono,
            validacionState = validacionNewUserUiState.validacionTelefono,
            onValueChange = {onNewUserEvent(NewUserEvent.TelefonoChanged(it))})
        Spacer(modifier = Modifier.padding(5.dp) )
        OutlinedTextFieldCalle(calleState = newUserUiState.calle,
            validacionState = validacionNewUserUiState.validacionCalle,
            onValueChange = {onNewUserEvent(NewUserEvent.CalleChanged(it))})
        Spacer(modifier = Modifier.padding(5.dp) )
        OutlinedTextFieldCiudad(ciudadState = newUserUiState.ciudad,
            validacionState = validacionNewUserUiState.validacionCiudad,
            onValueChange = {onNewUserEvent(NewUserEvent.CiudadChanged(it))})
        Spacer(modifier = Modifier.padding(5.dp) )

        if (esNuevoClienteState)
        {
            OutlinedTextFieldEmail(emailState = newUserUiState.correo,
                validacionState = validacionNewUserUiState.validacionLogin,
                onValueChange = {onNewUserEvent(NewUserEvent.LoginChanged(it))})
            Spacer(modifier = Modifier.padding(5.dp) )
            OutlinedTextFieldPassword(passwordState = newUserUiState.contraseña,
                validacionState = validacionNewUserUiState.validacionPassword,
                onValueChange = {onNewUserEvent(NewUserEvent.PasswordChanged(it))})
        }


        /*boton*/

        Button(
            onClick = {onNewUserEvent(NewUserEvent.onClickCrearCliente(onNavigateToLogin))},
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(if(esNuevoClienteState)"Crear cuenta" else "Modificar")
        }

        if (mostrarSnack) {
            if (mensaje.isNotEmpty()) Snackbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            ) {
                Text(text = mensaje)
            }
        }

    }
}




