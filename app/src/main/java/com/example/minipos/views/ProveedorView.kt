package com.example.minipos.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.minipos.models.Proveedor
import com.example.minipos.viewmodels.ProveedorViewModel

@Composable
fun ProveedorView(
    navController: NavController,
    viewModel: ProveedorViewModel
) {
    val openDialog = remember { mutableStateOf(false) }
    val proveedorAddUpdate = remember { mutableStateOf("") }
    val proveedor = remember { mutableStateOf(Proveedor()) }

}