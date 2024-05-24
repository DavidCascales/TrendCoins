package com.trendCoins.data.services.articulo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface ArticuloService {

    @GET("articulos/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(@Path("id") id: Int): Response<ArticuloApi>

    @GET("articulos")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(): Response<List<ArticuloApi>>

}





