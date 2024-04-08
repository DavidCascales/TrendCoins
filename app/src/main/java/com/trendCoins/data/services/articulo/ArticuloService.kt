package com.pmdm.tienda.data.room.articulo

import com.pmdm.tienda.data.services.RespuestaApi
import com.pmdm.tienda.data.services.articulo.ArticuloApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


interface ArticuloService {

    @POST("articulos")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun insert(@Body articulo: ArticuloApi): Response<RespuestaApi>

    @GET("articulos/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(@Path("id") id: Int): Response<ArticuloApi>

    @GET("articulos")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(): Response<List<ArticuloApi>>

}





