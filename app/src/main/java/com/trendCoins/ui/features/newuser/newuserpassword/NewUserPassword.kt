package com.pmdm.tienda.ui.features.newuser.newuserpassword


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import com.pmdm.tienda.ui.composables.CircularImageFromResource
import com.pmdm.tienda.ui.composables.OutlinedTextFieldEmail
import com.pmdm.tienda.ui.composables.OutlinedTextFieldPassword
import com.pmdm.tienda.ui.composables.TextWithLine
import com.pmdm.tienda.R
import com.pmdm.tienda.ui.features.newuser.newuserpassword.NewUserPasswordEvent
import com.pmdm.tienda.ui.features.newuser.newuserpassword.LoginPasswordUiState
import com.pmdm.tienda.ui.features.newuser.newuserpassword.ValidacionLoginPasswordUiState


@Composable
fun NuevoUsuarioPassword(
    esNuevoClienteState:Boolean,
    newUserPasswordUiState: LoginPasswordUiState,
    validadorNewUserUiState: ValidacionLoginPasswordUiState,
    newUserPasswordEvent: (NewUserPasswordEvent) -> Unit,
    onNavigateToLogin: ((correo: String, navOptions: NavOptions?) -> Unit)?

) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )

        {
            CircularImageFromResource(
                idImageResource = R.drawable.logearse,
                contentDescription = "Logearse"
            )
            Spacer(modifier = Modifier.height(20.dp))

            TextWithLine(
                texto = "Login y Password", color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextFieldEmail(
                modifier = Modifier.fillMaxWidth(),
                enabled = esNuevoClienteState,
                label = "Login",
                emailState = newUserPasswordUiState.login,
                validacionState = validadorNewUserUiState.validacionLogin,
                onValueChange = { newUserPasswordEvent(NewUserPasswordEvent.LoginChanged(it)) }
            )

            OutlinedTextFieldPassword(
                modifier = Modifier.fillMaxWidth(),
                label = "Password",
                passwordState = newUserPasswordUiState.password,
                validacionState = validadorNewUserUiState.validacionPassword,
                onValueChange = { newUserPasswordEvent(NewUserPasswordEvent.PasswordChanged(it)) }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(

                onClick = { newUserPasswordEvent(NewUserPasswordEvent.onClickCrearCliente(onNavigateToLogin)) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                Text(if(esNuevoClienteState)"Crear cuenta" else "Modificar")

            }

        }
    }

}
