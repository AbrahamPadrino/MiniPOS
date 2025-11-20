package com.example.minipos.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.minipos.R
import com.example.minipos.navigation.Views
import com.example.minipos.utils.Constantes
import com.example.minipos.utils.Permisos
import com.example.minipos.viewmodels.InicioViewModel
import com.example.minipos.viewmodels.MenuViewModel

@Composable
fun MenuView(
    navController: NavController,
    viewModel: MenuViewModel,
    inicioViewModel: InicioViewModel
) {
    Permisos()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.popBackStack()
                    inicioViewModel.logout()
                    navController.navigate(Views.InicioView.route)
                },
                containerColor = colorResource(id = R.color.gris_oscuro)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        MenuScreen(
            it,
            navController = navController
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    it: PaddingValues,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(12.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(Constantes.listMenu) {
                Card(
                    modifier = Modifier
                        .padding(12.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    onClick = {
                        when(it.categoria.toLowerCase()) {
                            "productos" -> navController.navigate(Views.ProductoView.route)
                            "proveedores" -> navController.navigate(Views.ProveedorView.route)
                            "ventas" -> navController.navigate(Views.VentaView.route)
                            "reportes" -> navController.navigate(Views.ReporteView.route)
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .padding(12.dp),
                            model = it.iconCategoria,
                            contentDescription = null,
                            contentScale = ContentScale.Inside
                        )

                        Text(
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            text = it.categoria.toUpperCase()
                        )
                    }
                }
            }
        }
    }
}