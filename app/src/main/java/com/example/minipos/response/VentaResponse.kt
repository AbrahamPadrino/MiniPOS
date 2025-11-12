package com.example.minipos.response

import com.example.minipos.models.DatosVenta

data class VentaResponse(
    val codigo: String,
    val mensaje: String,
    val resultado: List<DatosVenta>
)