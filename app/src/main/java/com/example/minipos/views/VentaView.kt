package com.example.minipos.views

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minipos.R
import com.example.minipos.utils.CaptureActivityPortrait
import com.example.minipos.viewmodels.VentaViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaView(
    navController: NavController,
    viewModel: VentaViewModel
) {
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "VENTAS")
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
        }
    ) {
        VentasScreen(
            paddingValues = it,
            openDialog = openDialog,
            viewModel = viewModel
        )
    }
}

@Composable
fun VentasScreen(
    paddingValues: PaddingValues,
    openDialog: MutableState<Boolean>,
    viewModel: VentaViewModel
) {
    val context = LocalContext.current

    val listaProductos by viewModel.listaProductos.collectAsState()
    var pago by remember { mutableStateOf("0.0") }
    val totalVenta by viewModel.totalVenta.collectAsState()

    var codigo by remember { mutableStateOf("") }

    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result ->
            codigo = result.contents ?: "No hay resultado del escaneado"
        }
    )

    var isPagado = remember { mutableStateOf(false) }

    if (isPagado.value) {
        codigo = ""
        pago = "0.0"
        isPagado.value = false
    }

    if (openDialog.value) {
        DialogCobro(
            openDialog = openDialog,
            pago = pago,
            totalVenta = totalVenta,
            isPagado = isPagado,
            viewModel = viewModel
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically)
                            .padding(4.dp)
                            .weight(5f),
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

                    IconButton(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(4.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .background(Color.Transparent),
                        onClick = {
                            viewModel.validarCampo(codigo)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "buscar"
                        )
                    }
                }
            }

            Divider(thickness = 4.dp)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(4.dp)
            ) {
                LazyColumn {
                    itemsIndexed(listaProductos) {index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(5f),
                                text = item.nomProducto
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(0.5f),
                                text = item.cantidad.toString()
                            )
                            IconButton(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(0.5f),
                                onClick = {
                                    viewModel.borrarProducto(index)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Borrar"
                                )
                            }
                        }
                    }
                }
            }

            Divider(thickness = 4.dp)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1.4f)
                            .padding(4.dp),
                        onClick = {
                            if (pago.toDouble() >= totalVenta) {
                                openDialog.value = true
                            } else {
                                Toast.makeText(context, "La cantidad es inferior al total a pagar", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text(text = "Cobrar")
                    }
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .align(Alignment.CenterVertically),
                        text = "Su pago:",
                    )
                    TextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .align(Alignment.CenterVertically),
                        value = pago,
                        onValueChange = {
                            pago = it
                        }
                    )
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .align(Alignment.CenterVertically),
                        text = "Total:"
                    )
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .align(Alignment.CenterVertically),
                        text = totalVenta.toString()
                    )
                }
            }
        }
    }
}

@Composable
fun DialogCobro(
    openDialog: MutableState<Boolean>,
    pago: String,
    totalVenta: Double,
    isPagado: MutableState<Boolean>,
    viewModel: VentaViewModel
) {
    AlertDialog(
        title = {
            Text(text = "CAMBIO")
        },
        text = {
            Column {
                Text(text = "Paga con: $pago")
                Text(text = "Total a Pagar: $totalVenta")
                val cambio = pago.toDouble() - totalVenta
                Text(text = "Cambio: $cambio")
            }
        },
        onDismissRequest = {
            openDialog.value = false
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.registrarVenta()
                    isPagado.value = true
                    openDialog.value = false
                }
            ) {
                Text(text = "Aceptar")
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