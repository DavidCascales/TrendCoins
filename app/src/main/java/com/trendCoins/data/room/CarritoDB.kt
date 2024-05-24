package com.trendCoins.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trendCoins.data.room.ArticuloCarrito.ArticuloCarritoEntity
import com.trendCoins.data.room.ArticuloCarrito.ArticuloCarritoDao

class ConvertidorDeTipo {
    @TypeConverter
    fun deListaDeEnterosAString(lista: List<Int>?): String {
        return Gson().toJson(lista)
    }

    @TypeConverter
    fun deStringAListaDeEnteros(cadena: String?): List<Int>? {
        val tipo = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(cadena, tipo)
    }
}
@TypeConverters(ConvertidorDeTipo::class)
@Database(
    entities = [ArticuloCarritoEntity::class],
    version = 1
)
abstract class CarritoDB : RoomDatabase() {
    abstract fun articulosCarrito(): ArticuloCarritoDao
    companion object {
        fun getDatabase(context: Context) = Room.databaseBuilder(
            context,
            CarritoDB::class.java, "carrito"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}