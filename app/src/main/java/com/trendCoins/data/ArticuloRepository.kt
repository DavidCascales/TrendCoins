package com.trendCoins.data


import com.trendCoins.data.services.articulo.ArticuloServiceImplementation
import com.trendCoins.models.Articulo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticuloRepository @Inject constructor(private val proveedorArticulo: ArticuloServiceImplementation) {



    suspend fun get(): List<Articulo> = withContext(Dispatchers.IO) {
        proveedorArticulo.get().toMutableList().toArticulos()
    }

    suspend fun get(id: Int): Articulo? = withContext(Dispatchers.IO) {
        proveedorArticulo.get(id)?.toArticulo()
    }

    suspend fun get(filtro: String): List<Articulo>? = withContext(Dispatchers.IO) {
        proveedorArticulo.get().filter { it.descripcion.contains(filtro) || it.tipo.contains(filtro)}?.toMutableList()?.toArticulos()
    }

    suspend fun get(ids: MutableList<Int>): List<Articulo>? =
        withContext(Dispatchers.IO) {
            var lista = mutableListOf<Articulo>()
            ids.forEach { lista.add(proveedorArticulo.get(it).toArticulo()) }
            lista
        }
}