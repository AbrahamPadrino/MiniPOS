package com.example.minipos.navigation

sealed class Views(
    val route: String
) {
    object SplashView: Views("splash_screen")
    object InicioView: Views("inicio")
    object MenuView: Views("menu")
    object ProductoView: Views("producto")
    object ProveedorView: Views("proveedor")
    object VentaView: Views("venta")
    object ReporteView: Views("reporte")
}