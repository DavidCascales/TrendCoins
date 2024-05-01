package com.pmdm.tienda.ui.features.newuser

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import com.pmdm.agenda.utilities.imagenes.Imagenes
import com.pmdm.tienda.ui.composables.OutlinedTextFieldCalle
import com.pmdm.tienda.ui.composables.OutlinedTextFieldCiudad
import com.pmdm.tienda.ui.composables.OutlinedTextFieldEmail
import com.pmdm.tienda.ui.composables.OutlinedTextFieldName
import com.pmdm.tienda.ui.composables.OutlinedTextFieldPassword
import com.pmdm.tienda.ui.composables.OutlinedTextFieldPhone
import com.pmdm.tienda.ui.features.newuser.datospersonales.DatosPersonales
import com.pmdm.tienda.ui.features.newuser.direccion.Direccion
import com.pmdm.tienda.ui.features.newuser.newuserpassword.NuevoUsuarioPassword
import com.pmdm.tienda.ui.features.newuser.datospersonales.DatosPersonalesEvent
import com.pmdm.tienda.ui.features.newuser.direccion.DireccionEvent
import com.pmdm.tienda.ui.features.newuser.newuserpassword.NewUserPasswordEvent
import com.trendCoins.ui.features.newuser.NewUserEvent
import com.trendCoins.utilities.RegistroSelectorDeImagenesConGetContent
import kotlinx.coroutines.launch


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
    Column {
        Image(modifier= Modifier
            .size(400.dp)
            .clickable {
                selectorDeImagenes.launch("image/*")
            },painter = if (newUserUiState.imagen ==null) rememberVectorPainter(image = Icons.Filled.ReportProblem) else
            BitmapPainter(Imagenes.base64ToBitmap(newUserUiState.imagen!!)),
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
        OutlinedTextFieldEmail(emailState = newUserUiState.correo,
            validacionState = validacionNewUserUiState.validacionLogin,
            onValueChange = {onNewUserEvent(NewUserEvent.LoginChanged(it))})
        Spacer(modifier = Modifier.padding(5.dp) )
        OutlinedTextFieldPassword(passwordState = newUserUiState.contraseña,
            validacionState = validacionNewUserUiState.validacionPassword,
            onValueChange = {onNewUserEvent(NewUserEvent.PasswordChanged(it))})

        /*boton*/

        Button(
            onClick = {onNewUserEvent(NewUserEvent.onClickCrearCliente(onNavigateToLogin))},
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Login")
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



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewUserScreen(
    newUserUiState: NewUserUiState,
    validacionNewUserUiState: ValidacionNewUserUiState,
    esNuevoClienteState:Boolean,
    mostrarSnack: Boolean,
    mensaje: String,
    incrementaPagina: Int,
    onDireccionEvent: (DireccionEvent) -> Unit,
    onDatosPersonalesEvent: (DatosPersonalesEvent) -> Unit,
    onNewUserPasswordEvent: (NewUserPasswordEvent) -> Unit,
    onNavigateToLogin: ((correo: String, navOptions: NavOptions?) -> Unit)?
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 3 })

    Box(
        modifier = Modifier.fillMaxHeight(),

        ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .wrapContentHeight()
                .padding(25.dp)

        ) { pagina ->

            when (pagina) {
                0 -> DatosPersonales(
                    esNuevoClienteState=esNuevoClienteState,
                    datosPersonalesUIState = newUserUiState.datosPersonalesUiState,
                    validadorDatosPersonalesUIState = validacionNewUserUiState.validacionDatosPersonalesUiState,
                    datosPersonalesEvent = {
                        onDatosPersonalesEvent(it)
                        if (it is DatosPersonalesEvent.OnClickSiguiente) {
                            scope.launch {
                                pagerState.scrollToPage(
                                    pagina + incrementaPagina
                                )
                            }
                        }
                    })

                1 -> Direccion(direccionUiState = newUserUiState.direccionUiState,
                    validadorDireccionUiState = validacionNewUserUiState.validacionDireccionUiState,
                    direccionEvent = {
                        onDireccionEvent(it)
                        if (it is DireccionEvent.OnClickSiguiente) {
                            scope.launch {
                                pagerState.scrollToPage(
                                    pagina + incrementaPagina
                                )
                            }
                        }
                    })

                2 -> NuevoUsuarioPassword(
                    esNuevoClienteState=esNuevoClienteState,
                    newUserPasswordUiState = newUserUiState.newUserPasswordUiState,
                    validadorNewUserUiState = validacionNewUserUiState.validacionLoginPasswordUiState,
                    newUserPasswordEvent = onNewUserPasswordEvent,
                    onNavigateToLogin=onNavigateToLogin
                )
            }
        }

        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(3) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(20.dp)
                )
            }
        }
        if (mostrarSnack) {
            if (mensaje.isNotEmpty()) Snackbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = mensaje)
            }
        }
    }
}



