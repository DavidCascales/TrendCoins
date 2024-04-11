package com.trendCoins.data.room.ArticuloCarrito

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articulosCarrito")
data class ArticuloCarritoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "correo_cliente")
    val correoCliente: String,

    @ColumnInfo(name = "descripcion")
    val descripcion: String,

    @ColumnInfo(name = "precio")
    val precio: Int,

    @ColumnInfo(name = "cantidad")
    val cantidad: Int,

    @ColumnInfo(name = "talla")
    val talla: String

)