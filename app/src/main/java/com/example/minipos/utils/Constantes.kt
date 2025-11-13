package com.example.minipos.utils

import com.example.minipos.R
import com.example.minipos.models.Menu


object Constantes {
    const val BASE_URL = "http://192.168.18.20:3000"
    const val DURACION_SPLASH_SCREEN: Long = 4000

    val listMenu = listOf(
        Menu("productos", R.drawable.boton_producto),
        Menu("proveedores", R.drawable.boton_proveedor),
        Menu("ventas", R.drawable.boton_ventas),
        Menu("reportes", R.drawable.boton_reporte)
    )
}