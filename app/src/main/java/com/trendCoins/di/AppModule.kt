package com.trendCoins.di

import android.content.Context
import com.pmdm.tienda.data.room.articulo.ArticuloService
import com.pmdm.tienda.data.services.cliente.ClienteService
import com.trendCoins.data.ArticuloCarritoRepository
import com.trendCoins.data.room.ArticuloCarrito.ArticuloCarritoDao
import com.trendCoins.data.room.CarritoDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val timeout = 10L
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) : Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://ubuntuproyectodam.westeurope.cloudapp.azure.com:8080/trendcoins/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun provideClienteService(
        retrofit: Retrofit
    ): ClienteService = retrofit.create(ClienteService::class.java)

    @Provides
    @Singleton
    fun provideArticuloService(
        retrofit: Retrofit
    ): ArticuloService = retrofit.create(ArticuloService::class.java)



    @Provides
    @Singleton
    fun provideCarritoDatabase(
        @ApplicationContext context: Context
    ): CarritoDB = CarritoDB.getDatabase(context)

    @Provides
    @Singleton
    fun provideArticuloCarritoDao(
        db: CarritoDB
    ): ArticuloCarritoDao = db.articulosCarrito()

    @Provides
    @Singleton
    fun provideArticuloCarritoRepository(
        articuloDao: ArticuloCarritoDao
    ): ArticuloCarritoRepository =
        ArticuloCarritoRepository(articuloDao)
}