package com.trendCoins.utilities

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.pmdm.agenda.utilities.imagenes.Imagenes

@Composable
fun RegistroSelectorDeImagenesConGetContent(
    onFotoCambiada: (ImageBitmap) -> Unit
): ManagedActivityResultLauncher<String, Uri?> {
    val context = LocalContext.current
    return rememberLauncherForActivityResult(
        contract=ActivityResultContracts.GetContent(),
        onResult={ uri ->
        uri?.let {
            onFotoCambiada(Imagenes.bitmapFromURI(uri,context))
        }
    })
}