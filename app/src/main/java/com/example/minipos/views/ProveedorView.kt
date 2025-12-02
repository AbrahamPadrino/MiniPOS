package com.example.minipos.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minipos.R
import com.example.minipos.models.Proveedor
import com.example.minipos.viewmodels.ProveedorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProveedorView(
    navController: NavController,
    viewModel: ProveedorViewModel
) {
    val openDialog = remember { mutableStateOf(false) }
    val proveedorAddUpdate = remember { mutableStateOf("") }
    val proveedor = remember { mutableStateOf(Proveedor()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "PROVEEDORES")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "regresar"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp),
                onClick = {
                    proveedorAddUpdate.value = "add"
                    openDialog.value = true
                },
                containerColor = colorResource(id = R.color.gris_oscuro)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        if (openDialog.value) {
            DialogAddUpdateProveedor(
                openDialog = openDialog,
                proveedorAddUpdate = proveedorAddUpdate,
                viewModel = viewModel,
                proveedor = proveedor
            )
        }

        ProveedoresScreen(
            paddingValues = it,
            openDialog = openDialog,
            proveedorAddUpdate = proveedorAddUpdate,
            proveedor = proveedor,
            viewModel = viewModel
        )
    }

}



@Composable
fun ProveedoresScreen(
    paddingValues: PaddingValues,
    openDialog: MutableState<Boolean>,
    proveedorAddUpdate: MutableState<String>,
    proveedor: MutableState<Proveedor>,
    viewModel: ProveedorViewModel
) {
    TODO("Not yet implemented")
}

@Composable
fun DialogAddUpdateProveedor(
    openDialog: MutableState<Boolean>,
    proveedorAddUpdate: MutableState<String>,
    viewModel: ProveedorViewModel,
    proveedor: MutableState<Proveedor>
) {
    TODO("Not yet implemented")
}