package com.example.minipos.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.minipos.viewmodels.InicioViewModel
import com.example.minipos.viewmodels.MenuViewModel
import com.example.minipos.viewmodels.ProductoViewModel
import com.example.minipos.viewmodels.ProveedorViewModel
import com.example.minipos.viewmodels.ReporteViewModel
import com.example.minipos.viewmodels.VentaViewModel
import com.example.minipos.views.InicioView
import com.example.minipos.views.MenuView
import com.example.minipos.views.ProductoView
import com.example.minipos.views.ProveedorView
import com.example.minipos.views.ReporteView
import com.example.minipos.views.SplashView
import com.example.minipos.views.VentaView

@SuppressLint("ViewModelConstructorInComposable")
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