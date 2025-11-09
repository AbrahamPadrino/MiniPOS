package com.example.minipos.models

import com.google.gson.annotations.SerializedName

data class Producto(
    @SerializedName("almacen")
    var almancen: Int = 0,
    var codProducto: String = "",
    var descripcion: String = "",
    var nomProducto: String = "",
    var nomProveedor: String = "",
    var precio: Double = 0.0
)

data class ProductoVenta(
    @SerializedName("almacen")
    var almancen: Int,
    var codProducto: String,
    var descripcion: String,
    var nomProducto: String,
    var nomProveedor: String,
    var precio: Double,
    var cantidad: Int
)