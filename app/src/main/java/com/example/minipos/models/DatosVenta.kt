package com.example.minipos.models

data class DatosVenta(
    val fechaVenta: String,
    val idVenta: String,
    val codProductos: List<ProdsVenta>,
    val total: Double
)