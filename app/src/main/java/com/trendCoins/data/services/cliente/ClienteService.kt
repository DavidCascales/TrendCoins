package com.trendCoins.data.services.cliente


import com.trendCoins.data.services.RespuestaApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path



interface ClienteService {
    @GET("clientes")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(): Response<List<ClienteApi>>

    @GET("clientes/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(@Path("id") correo: String):Response<ClienteApi>




    @POST("clientes")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun insert(@Body cliente: ClienteApi): Response<RespuestaApi>


    @PUT("clientes")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun update( @Body cliente: ClienteApi): Response<RespuestaApi>




}