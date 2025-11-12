package com.example.minipos.response

import com.example.minipos.models.Proveedor

data class ProveedorResponse(
    val codigo: String,
    val mensaje: String,
    val resultado: MutableList<Proveedor>
)
