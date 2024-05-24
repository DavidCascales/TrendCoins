package com.trendCoins.data.services.cliente

import android.util.Log
import com.trendCoins.data.services.ApiServicesException

import javax.inject.Inject

class ClienteServiceImplementation @Inject constructor(
    private val clienteService: ClienteService
) {
    private val logTag: String = "OkHttp"
    suspend fun get(): List<ClienteApi> {
        val mensajeError = "No se han podido obtener los clientes"
        try {
            val response = clienteService.get()
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
    suspend fun get(correo:String): ClienteApi {
        val mensajeError = "No se han podido obtener el cliente para el $correo"
        try {
            val response = clienteService.get(correo)
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

    suspend fun insert(cliente: ClienteApi) {
        val mensajeError ="No se ha podido añadir el cliente"
        try {
            val response = clienteService.insert(cliente)
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

    suspend fun update(cliente: ClienteApi) {
        val mensajeError = "No se ha podido actualizar el cliente"
        try {
            val response = clienteService.update( cliente)
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