package com.pmdm.agenda.utilities.imagenes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

class Imagenes {
    companion object {
        fun base64ToAndroidBitmap(base64ImageString: String): Bitmap =
        Base64.decode(base64ImageString, Base64.DEFAULT).let { decodedString ->
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString!!.size)
        }

        fun base64ToBitmap(base64ImageString: String) =
            base64ToAndroidBitmap(base64ImageString).asImageBitmap()

        fun androidBitmapToBase64(bitmap: Bitmap): String =ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, this)
        }.use { stream ->
            Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
        }

        fun bitmapToBase64(bitmap: ImageBitmap): String =
            androidBitmapToBase64(bitmap.asAndroidBitmap())

        fun base64ToBitmap(biteArray: ByteArray?): ImageBitmap? =
            biteArray?.let {
                BitmapFactory.decodeByteArray(biteArray, 0, biteArray.size).asImageBitmap()
            }

        fun bitmapToBase64(bitmap: ImageBitmap?): ByteArray? =
            bitmap?.let {
                val stream = ByteArrayOutputStream()
                bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 70, stream)
                stream.toByteArray()
            }

        fun blobToBase64(value: ByteArray?): String? =
            value?.let { Base64.encodeToString(value, Base64.DEFAULT) }

        fun base64ToBlob(value: String?): ByteArray? =
            value?.let { Base64.decode(value, Base64.DEFAULT) }

        fun androidBitmapFromRerouceId(recurso: Int, context: Context): Bitmap =
            BitmapFactory.decodeResource(context.resources, recurso)

        fun androidBitmapFromURI(uri: Uri, context: Context): Bitmap {
            val contextResolver = context.contentResolver
            val source = ImageDecoder.createSource(contextResolver, uri)
            return ImageDecoder.decodeBitmap(source)
        }

        fun bitmapFromURI(uri: Uri, context: Context) =
            androidBitmapFromURI(uri, context).asImageBitmap()
    }
}