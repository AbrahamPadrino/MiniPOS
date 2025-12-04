package com.example.minipos.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minipos.R
import com.example.minipos.models.Proveedor
import com.example.minipos.utils.LanzarEmail
import com.example.minipos.utils.LanzarTelefono
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
    val context = LocalContext.current
    val listaProveedores by viewModel.listaProveedores.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        items(listaProveedores) {
            Card(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp,
                        vertical = 8.dp
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .weight(6f)
                            .align(Alignment.CenterVertically),
                        text = it.nomProveedor
                    )

                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        onClick = {
                            LanzarTelefono().abrirTelefono(
                                context = context,
                                telefono = it.telefono
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "llamar"
                        )
                    }

                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        onClick = {
                            LanzarEmail().lanzarEmail(
                                context = context,
                                email = it.email
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "email"
                        )
                    }

                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        onClick = {
                            proveedorAddUpdate.value = "edit"
                            openDialog.value = true
                            proveedor.value = it
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "editar"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DialogAddUpdateProveedor(
    openDialog: MutableState<Boolean>,
    proveedorAddUpdate: MutableState<String>,
    viewModel: ProveedorViewModel,
    proveedor: MutableState<Proveedor>
) {
    val context = LocalContext.current

    var nomProveedor by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    if (proveedorAddUpdate.value == "edit") {
        nomProveedor = proveedor.value.nomProveedor
        email = proveedor.value.email
        telefono = proveedor.value.telefono
    }

    AlertDialog(
        title = {
            if (proveedorAddUpdate.value == "add") {
                Text(text = "Agregar Proveedor")
            } else if(proveedorAddUpdate.value == "edit") {
                Text(text = "Editar Proveedor")
            }
        },
        text = {
            Column {
                if (proveedorAddUpdate.value == "edit") {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(vertical = 4.dp),
                        value = nomProveedor,
                        onValueChange = {
                            nomProveedor = it
                        },
                        label = {
                            Text(text = "Proveedor")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        maxLines = 1,
                        readOnly = true
                    )
                } else {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(vertical = 4.dp),
                        value = nomProveedor,
                        onValueChange = {
                            nomProveedor = it
                        },
                        label = {
                            Text(text = "Proveedor")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        maxLines = 1
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = {
                        Text(text = "Email")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    maxLines = 1
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    value = telefono,
                    onValueChange = {
                        telefono = it
                    },
                    label = {
                        Text(text = "Telefono")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    maxLines = 1
                )
            }
        },
        onDismissRequest = {
            openDialog.value = false
        },
        confirmButton = {
            Button(
                onClick = {
                    if (proveedorAddUpdate.value == "add") {
                        proveedor.value.nomProveedor = nomProveedor
                        proveedor.value.email = email
                        proveedor.value.telefono = telefono
                    } else if (proveedorAddUpdate.value == "edit") {
                        proveedor.value.nomProveedor = nomProveedor
                        proveedor.value.email = email
                        proveedor.value.telefono = telefono
                    }

                    if (viewModel.validarCampos(proveedor)) {
                        if (proveedorAddUpdate.value == "add") {
                            viewModel.agregarProveedor(proveedor = proveedor.value)
                        } else if (proveedorAddUpdate.value == "edit") {
                            viewModel.editarProveedor(proveedor = proveedor.value)
                        }
                        proveedorAddUpdate.value = ""
                        openDialog.value = false
                    } else {
                        Toast.makeText(context, "Debes llenar todos los campos", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text(text = "Aceptar")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text(text = "Cancelar")
            }
        }
    )
}