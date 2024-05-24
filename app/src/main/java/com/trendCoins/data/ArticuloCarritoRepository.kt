package com.trendCoins.data

import com.trendCoins.data.room.ArticuloCarrito.ArticuloCarritoDao
import com.trendCoins.models.ArticuloCarrito
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticuloCarritoRepository @Inject constructor(private val proveedorcarrito: ArticuloCarritoDao) {



    suspend fun get(login: String): List<ArticuloCarrito> = withContext(Dispatchers.IO) {
        proveedorcarrito.get(login).toMutableList().toArticuloCarrito()
    }

    suspend fun delete(login: String) = withContext(Dispatchers.IO) { proveedorcarrito.delete(login) }


    suspend fun insert(articulocarrito: ArticuloCarrito) =
        withContext(Dispatchers.IO) { proveedorcarrito.insert(articulocarrito.toArticuloCarritoEntity()) }

    suspend fun update(articuloCarrito: ArticuloCarrito) =
        withContext(Dispatchers.IO) { proveedorcarrito.update(articuloCarrito.toArticuloCarritoEntity()) }

    suspend fun deleteArticulo(login: String, descripcion: String, talla: String) =  withContext(Dispatchers.IO) { proveedorcarrito.deleteArticulo(login,descripcion, talla) }
}