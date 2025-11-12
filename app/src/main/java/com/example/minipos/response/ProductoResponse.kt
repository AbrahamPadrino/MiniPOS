package com.example.minipos.response

import com.example.minipos.models.Producto

data class ProductoResponse(
    val codigo: String,
    val mensaje: String,
    val resultado: MutableList<Producto>
)