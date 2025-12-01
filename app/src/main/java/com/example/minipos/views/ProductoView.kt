package com.example.minipos.views

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minipos.R
import com.example.minipos.models.Producto
import com.example.minipos.utils.CaptureActivityPortrait
import com.example.minipos.viewmodels.ProductoViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoView(
    navController: NavController,
    viewModel: ProductoViewModel
) {
    val openDialog = remember { mutableStateOf(false) }           // Mostrar/Ocultar el dialog para registro de producto
    val productoAddUpdate = remember { mutableStateOf("") }      // Controla el valor de la acción a realizar
    val productoEdit = remember { mutableStateOf(Producto()) }  // Contiene el valor del objeto que se envia al servidor.

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "PRODUCTOS")
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
                    productoAddUpdate.value = "add"
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
            DialogAddEditProducto(
                productoAddUpdate = productoAddUpdate,
                openDialog = openDialog,
                viewModel = viewModel,
                productoEdit = productoEdit
            )
        }

        ProductosScreen(
            it,
            viewModel = viewModel,
            productoAddUpdate = productoAddUpdate,
            openDialog = openDialog,
            productoEdit = productoEdit
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(
    paddingValues: PaddingValues,
    viewModel: ProductoViewModel,
    productoAddUpdate: MutableState<String>,
    openDialog: MutableState<Boolean>,
    productoEdit: MutableState<Producto>
) {
    viewModel.obtenerProductos()
    val listaProductos by viewModel.listaProductos.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        items(listaProductos) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                onClick = { /*TODO*/ },
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize()
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(5f)
                                .align(Alignment.CenterVertically),
                            textAlign = TextAlign.Center,
                            text = it.codProducto
                        )

                        IconButton(
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically),
                            onClick = {
                                productoAddUpdate.value = "edit"
                                openDialog.value = true
                                productoEdit.value = it
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "editar"
                            )
                        }

                        IconButton(
                            modifier = Modifier
                                .weight(1f),
                            onClick = {
                                viewModel.borrarProducto(it.codProducto)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "borrar"
                            )
                        }
                    }

                    Divider(
                        Modifier
                            .fillMaxSize()
                            .height(2.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(vertical = 4.dp),
                        text = "Producto: ${it.nomProducto}"
                    )

                    Text(
                        modifier = Modifier
                            .padding(vertical = 4.dp),
                        text = "Descripción: ${it.descripcion}"
                    )

                    Text(
                        modifier = Modifier
                            .padding(vertical = 4.dp),
                        text = "Proveedor: ${it.nomProveedor}"
                    )

                    Divider(
                        Modifier
                            .fillMaxSize()
                            .height(2.dp)
                    )

                    Row(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Almacen")
                            Text(text = "${it.almancen}")
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Precio")
                            Text(text = "$${it.precio}")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAddEditProducto(
    productoAddUpdate: MutableState<String>,
    openDialog: MutableState<Boolean>,
    viewModel: ProductoViewModel,
    productoEdit: MutableState<Producto>
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    viewModel.obtenerProveedores()
    val listaProveedores by viewModel.listaProveedodes.collectAsState()

    var showProveedores by remember { mutableStateOf(false) }
    var proveedorSeleccionado by remember { mutableStateOf("Selecciona un proveedor") }

    var codigo by remember { mutableStateOf("") }
    var nomProducto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var almacen by remember { mutableStateOf("") }

    if (productoAddUpdate.value == "edit") {
        codigo = productoEdit.value.codProducto
        nomProducto = productoEdit.value.nomProducto
        descripcion = productoEdit.value.descripcion
        precio = productoEdit.value.precio.toString()
        almacen = productoEdit.value.almancen.toString()
        proveedorSeleccionado = productoEdit.value.nomProveedor
    }

    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result ->
            codigo = result.contents ?: "No hay resultado leido"
        }
    )

    AlertDialog(
        title = {
            if (productoAddUpdate.value == "add") {
                Text(text = "AGREGAR PRODUCTO")
            } else if (productoAddUpdate.value == "edit") {
                Text(text = "EDITAR PRODUCTO")
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (productoAddUpdate.value == "add") {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(8.dp),
                        value = codigo,
                        onValueChange = {
                            codigo = it
                        },
                        singleLine = true,
                        maxLines = 1,
                        label = {
                            Text(text = "Codigo")
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    val scanOptions = ScanOptions()
                                    scanOptions.setBeepEnabled(true)
                                    scanOptions.setCaptureActivity(CaptureActivityPortrait::class.java)
                                    scanOptions.setOrientationLocked(false)
                                    scanLauncher.launch(scanOptions)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_codigo_barras),
                                    contentDescription = "escanear"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                } else if(productoAddUpdate.value == "edit") {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(8.dp),
                        value = codigo,
                        onValueChange = {
                            codigo = it
                        },
                        readOnly = true,
                        singleLine = true,
                        maxLines = 1,
                        label = {
                            Text(text = "Codigo")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp),
                    value = nomProducto,
                    onValueChange = {
                        nomProducto = it
                    },
                    singleLine = true,
                    maxLines = 1,
                    label = {
                        Text(text = "Producto")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp),
                    value = descripcion,
                    onValueChange = {
                        descripcion = it
                    },
                    singleLine = true,
                    maxLines = 5,
                    minLines = 5,
                    label = {
                        Text(text = "Descripción")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp),
                    value = precio,
                    onValueChange = {
                        precio = it
                    },
                    singleLine = true,
                    maxLines = 1,
                    label = {
                        Text(text = "Pecio")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp),
                    value = almacen,
                    onValueChange = {
                        almacen = it
                    },
                    singleLine = true,
                    maxLines = 1,
                    label = {
                        Text(text = "Almacen")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    expanded = showProveedores,
                    onExpandedChange = {
                        showProveedores = !showProveedores
                    }
                ) {
                    keyboardController?.hide()

                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        value = proveedorSeleccionado,
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showProveedores)},
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = showProveedores,
                        onDismissRequest = {
                            showProveedores = false
                        }
                    ) {
                        listaProveedores.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = s.toString())
                                },
                                onClick = {
                                    if (s.toString() != "") {
                                        proveedorSeleccionado = s.nomProveedor
                                    }
                                    showProveedores = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }
        },
        onDismissRequest = {
            openDialog.value = false
        },
        confirmButton = {
            Button(
                onClick = {
                    productoEdit.apply {
                        this.value.codProducto = codigo
                        this.value.nomProducto = nomProducto
                        this.value.descripcion = descripcion
                        this.value.nomProveedor = proveedorSeleccionado
                        this.value.precio = precio.toDouble()
                        this.value.almancen = almacen.toInt()
                    }

                    if (viewModel.validarProducto(productoEdit.value)) {
                        if (productoAddUpdate.value == "add") {
                            viewModel.agregarProducto(productoEdit.value)
                        } else if(productoAddUpdate.value == "edit") {
                            viewModel.actualizarProducto(productoEdit.value)
                        }

                        openDialog.value = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.verde_oscuro)
                )
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    openDialog.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.rojo_oscuro)
                )
            ) {
                Text("Cancelar")
            }
        }
    )
}


