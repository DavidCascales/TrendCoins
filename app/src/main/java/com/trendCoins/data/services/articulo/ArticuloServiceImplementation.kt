package com.pmdm.tienda.data.services.articulo

import android.util.Log
import com.pmdm.tienda.data.room.articulo.ArticuloService
import com.pmdm.tienda.data.services.ApiServicesException
import javax.inject.Inject

class ArticuloServiceImplementation @Inject constructor(
    private val articuloService: ArticuloService
) {
    private val logTag: String = "OkHttp"
    suspend fun get(): List<ArticuloApi> {
        val mensajeError = "No se han podido obtener los articulos"
        try {
            val response = articuloService.get()
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                val dato = response.body()
                return dato ?: throw ApiServicesException("No hay datos")
            } else {
                val body = response.errorBody()?.string()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
                throw ApiServicesException(mensajeError)
            }
        }
        catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            throw ApiServicesException(mensajeError)
        }
    }

    suspend fun get(id: Int): ArticuloApi {
        val mensajeError = "No se han podido obtener el articulo con id = $id"
        try {
            val response = articuloService.get(id)
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                val dato = response.body()

                return dato ?: throw ApiServicesException("No hay datos")

            } else {
                val body = response.errorBody()?.string()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
                throw ApiServicesException(mensajeError)
            }
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            throw ApiServicesException(mensajeError)
        }
    }

    suspend fun insert(articulo: ArticuloApi) {
        val mensajeError ="No se ha podido añadir el articulo"
        try {
            val response = articuloService.insert(articulo)
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                // Aquí response.body() es un objeto de tipo RespuestaApi
                // que simplemente logeamos si no es null.
                Log.d(logTag, response.body()?.toString() ?: "No hay respuesta")
            } else {
                val body = response.errorBody()?.string()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
                throw ApiServicesException(mensajeError)
            }
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            throw ApiServicesException(mensajeError)
        }
    }

}