package com.example.minipos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavManager() {
    val navController = rememberNavController()

    val inicioViewModel = InicioViewModel()
    val menuViewModel = MenuViewModel()
    val productoViewModel = ProductoViewModel()
    val proveedorViewModel = ProveedorViewModel()
    val ventaViewModel = VentaViewModel()
    val reporteViewModel = ReporteViewModel()

    NavHost(
        navController = navController,
        startDestination = Views.SplashView.route
    ) {
        composable(Views.SplashView.route) {
            SplashView(navController = navController)
        }

        composable(Views.InicioView.route) {
            InicioView(
                navController = navController,
                viewModel = inicioViewModel
            )
        }

        composable(Views.MenuView.route) {
            MenuView(
                navController = navController,
                viewModel = menuViewModel,
                inicioViewModel = inicioViewModel
            )
        }

        composable(Views.ProductoView.route) {
            ProductoView(
                navController = navController,
                viewModel = productoViewModel
            )
        }

        composable(Views.ProveedorView.route) {
            ProveedorView(
                navController = navController,
                viewModel = proveedorViewModel
            )
        }

        composable(Views.VentaView.route) {
            VentaView(
                navController = navController,
                viewModel = ventaViewModel
            )
        }

        composable(Views.ReporteView.route) {
            ReporteView(
                navController = navController,
                viewModel = reporteViewModel
            )
        }
    }
}