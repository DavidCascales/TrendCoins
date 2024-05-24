package com.trendCoins.data

import com.trendCoins.data.services.ApiServicesException
import com.trendCoins.data.services.cliente.ClienteServiceImplementation
import com.trendCoins.models.Cliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ClienteRepository @Inject constructor(private val proveedorClientes: ClienteServiceImplementation) {

    suspend fun get(): List<Cliente> =
        withContext(Dispatchers.IO) { proveedorClientes.get().toClientes() }

    suspend fun getClienteCorreo(correo: String): Cliente = withContext(Dispatchers.IO) {
        proveedorClientes.get().find { it.correo==correo }?.toCliente()?:Cliente()
    }


    suspend fun insert(cliente: Cliente) =
        withContext(Dispatchers.IO) { proveedorClientes.insert(cliente.toClienteApi()) }

    suspend fun update(cliente: Cliente) =
        withContext(Dispatchers.IO) { proveedorClientes.update(cliente.toClienteApi()) }

    suspend fun actualizaFavoritos(correo: String, deseados: List<Int>) =
        withContext(Dispatchers.IO) {

            try {
               var cliente = proveedorClientes.get(correo)

            cliente=cliente.copy(deseados=deseados.toMutableList().aString())
            proveedorClientes.update(cliente)
            }catch(e: ApiServicesException){}
        }

}

