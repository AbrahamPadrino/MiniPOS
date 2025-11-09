package com.example.minipos.models

data class VentasSend(
    val idVenta: String,
    val codProductos: String,
    val fechaVenta: String,
    val total: Double
)
