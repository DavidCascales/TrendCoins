package com.trendCoins.data.room.ArticuloCarrito

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ArticuloCarritoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articulosCarrito : ArticuloCarritoEntity)

    @Update
    suspend fun update(articulosCarrito : ArticuloCarritoEntity)

    /*@Query("SELECT * FROM articulosCarrito WHERE id=:id")
    suspend fun get(id:Int): ArticulosCarritoEntity*/
    @Query("SELECT * FROM articulosCarrito WHERE correo_cliente  LIKE '%' || :correo || '%'")
    suspend fun get(correo:String):  List<ArticuloCarritoEntity>

    /*@Query("SELECT * FROM articulosCarrito")
    suspend fun get(): List<ArticulosCarritoEntity>*/
    @Query("DELETE FROM articulosCarrito WHERE correo_cliente =:correo")
    suspend fun delete(correo :String)

    @Query("DELETE FROM articulosCarrito WHERE correo_cliente =:correo AND descripcion =:descripcion AND talla =:talla")
    suspend fun deleteArticulo(correo :String , descripcion :String , talla :String)

}