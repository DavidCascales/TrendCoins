package com.trendCoins.data



import com.pmdm.tienda.data.services.articulo.ArticuloApi
import com.pmdm.tienda.data.services.cliente.ClienteApi
import com.trendCoins.data.room.ArticuloCarrito.ArticuloCarritoEntity
import com.trendCoins.models.Articulo
import com.trendCoins.models.ArticuloCarrito
import com.trendCoins.models.Cliente


//region usuarioEntity
/*fun UsuarioApi.toUsuario(): Usuario = Usuario(this.login.desformateaCorreo(), this.password)
fun MutableList<UsuarioApi>.toUsuarios(): List<Usuario> =
    this.map { it.toUsuario() }
fun Usuario.toUsuarioApi(): UsuarioApi= UsuarioApi(this.login.formateaCorreo(), this.password)
//endregion
//region ClienteApi*/
fun MutableList<Cliente>.toClientesApi(): List<ClienteApi> =
    this.map { it.toClienteApi() }

fun Cliente.toClienteApi(): ClienteApi =
    ClienteApi(
         this.correo,this.contraseña, this.nombre, this.telefono,this.imagen, this.deseados.aString(),
            this.calle,
            this.ciudad,
            this.puntos,

    )

fun List<ClienteApi>.toClientes(): List<Cliente> =
    this.map { it.toCliente() }

fun ClienteApi.toCliente(): Cliente = Cliente(
    this.correo,this.password, this.nombre, this.telefono,this.imagen, this.deseados.aMutableListInt(),
    this.calle,
    this.ciudad,
    this.puntos,
)
//endregion
//region ArticuloEntity
fun ArticuloApi.toArticulo(): Articulo =
    Articulo(this.id, this.imagen, this.descripcion,this.precio, this.tipo)

fun MutableList<ArticuloApi>.toArticulos(): List<Articulo> =
    this.map { it.toArticulo() }

fun Articulo.toArticuloApi(): ArticuloApi =
    ArticuloApi(this.id, this.imagen, this.descripcion,this.precio, this.tipo)

//endregion
//region PedidoEntity


private fun String.aMutableListInt(): MutableList<Int> {
    return this.split(",").map { if(it!="") it.toInt()else -1 }.filter { it!=-1 }.toMutableList()
}

 fun MutableList<Int>.aString(): String =
    this.joinToString (separator=",")
/*
fun String.formateaCorreo():String
{
    return this.replace('@','_').replace('.','-')
}
fun String.desformateaCorreo():String
{
    return this.replace('_','@').replace('-','.')
}*/



//region ArticuloEntity
fun ArticuloCarritoEntity.toArticuloCarrito(): ArticuloCarrito =
    ArticuloCarrito(this.id, this.correoCliente,this.descripcion, this.precio,this.cantidad,this.talla)

fun MutableList<ArticuloCarritoEntity>.toArticuloCarrito(): List<ArticuloCarrito> =
    this.map { it.toArticuloCarrito() }

fun ArticuloCarrito.toArticuloCarritoEntity(): ArticuloCarritoEntity =
    ArticuloCarritoEntity(this.id, this.correoCliente,this.descripcion, this.precio,this.cantidad,this.talla)

//endregion


