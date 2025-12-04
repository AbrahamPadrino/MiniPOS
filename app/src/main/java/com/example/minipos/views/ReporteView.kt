package com.example.minipos.views

import android.app.DatePickerDialog
import android.content.Context
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minipos.navigation.Views
import com.example.minipos.utils.ColorAleatorio
import com.example.minipos.viewmodels.ReporteViewModel
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.itextpdf.text.BaseColor
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Font
import com.itextpdf.text.FontFactory
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReporteView(
    navController: NavController,
    viewModel: ReporteViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "REPORTES")
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
        ReporteScreen(
            paddingValues = it,
            viewModel = viewModel
        )
    }
}

@Composable
fun ReporteScreen(
    paddingValues: PaddingValues,
    viewModel: ReporteViewModel
) {
    val context = LocalContext.current

    val fechaInicio = remember { mutableStateOf("") }
    val fechaFinal = remember { mutableStateOf("") }
    val seleccionButton = remember { mutableStateOf("") }
    val showCalendario = remember { mutableStateOf(false) }

    if(showCalendario.value) {
        if (seleccionButton.value == "inicio") {
            mostrarCalendario(
                fechaInicio
            )
        } else if(seleccionButton.value == "final") {
            mostrarCalendario(
                fechaFinal
            )
        }
        seleccionButton.value = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 4.dp)
                .align(Alignment.CenterHorizontally),
            value = fechaInicio.value,
            onValueChange = {
                fechaInicio.value = it
            },
            readOnly = true,
            label = {
                Text(text = "Selecciona fecha inicial")
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                            seleccionButton.value = "inicio"
                            showCalendario.value = true
                        },
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "fecha"
                )
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 4.dp)
                .align(Alignment.CenterHorizontally),
            value = fechaFinal.value,
            onValueChange = {
                fechaFinal.value = it
            },
            readOnly = true,
            label = {
                Text(text = "Selecciona fecha final")
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                            seleccionButton.value = "final"
                            showCalendario.value = true
                        },
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "fecha",

                    )
            }
        )

        if (
            fechaInicio.value.isNotEmpty() &&
            fechaFinal.value.isNotEmpty()
        ) {
            viewModel.obtenerPeriodoVentas(
                fechaInicio.value,
                fechaFinal.value
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(4.dp),
                    onClick = {
                        fechaInicio.value = ""
                        fechaFinal.value = ""
                    }
                ) {
                    Text(text = "Limpiar Campos")
                }

                Button(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(4.dp),
                    onClick = {
                        guardarInformacionPDF(
                            context = context,
                            fechaInicio = fechaInicio,
                            fechaFinal = fechaFinal,
                            viewModel = viewModel
                        )
                    }
                ) {
                    Text(text = "Generar Reporte")
                }
            }

            mostrarGrafica(
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun mostrarGrafica(
    viewModel: ReporteViewModel
) {
    val datos by viewModel.datosGraficar.collectAsState()
    //Log.d("DATOS", datos.toString())

    var barras = ArrayList<BarChartData.Bar>()

    datos.mapIndexed { index, datosGrafica ->
        barras.add(
            BarChartData.Bar(
                label = datosGrafica.etiqueta.split("T")[0],
                value = datosGrafica.valor,
                color = ColorAleatorio().colorAleatorio()
            )
        )
    }

    BarChart(
        modifier = Modifier
            .padding(30.dp, 80.dp)
            .height(300.dp),
        labelDrawer = SimpleValueDrawer(
            drawLocation = SimpleValueDrawer.DrawLocation.XAxis
        ),
        barChartData = BarChartData(
            bars = barras
        )
    )
}

@Composable
fun mostrarCalendario(
    fecha: MutableState<String>
) {
    val anio: Int
    val mes: Int
    val dia: Int
    val mCalendar = Calendar.getInstance()
    anio = mCalendar.get(Calendar.YEAR)
    mes = mCalendar.get(Calendar.MONTH)
    dia = mCalendar.get(Calendar.DAY_OF_MONTH)

    val mDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, anio: Int, mes: Int, dia: Int ->

            var diaAux = dia.toString()
            var mesAux = mes.toString()

            if ((mes + 1) < 10) {
                mesAux = "0${mes + 1}"
            }

            if (dia < 10) {
                diaAux = "0$dia"
            }

            fecha.value = "$anio-${mesAux}-$diaAux"
        }, anio, mes, dia
    )

    mDatePickerDialog.show()
}

fun guardarInformacionPDF(
    context: Context,
    fechaInicio: MutableState<String>,
    fechaFinal: MutableState<String>,
    viewModel: ReporteViewModel
) {
    fechaInicio.value.substring(0 .. 9)
    fechaFinal.value.substring(0 .. 9)

    val nomCarpeta = "MiniPOS"
    val nomArchivo = "Reporte_${fechaInicio.value}_${fechaFinal.value}.pdf"

    var totalVentasReporte = 0.0

    try {
        //val ruta = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absoluteFile.toString()
        val ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val folder = File(ruta, nomCarpeta)

        if (!folder.exists()) {
            folder.mkdirs()
        }

        val file = File(folder, nomArchivo)
        val fos = FileOutputStream(file)

        val documento = com.itextpdf.text.Document()
        PdfWriter.getInstance(documento, fos)

        documento.open()

        val titulo = Paragraph(
            "REPORTE DE VENTAS\nPeriodo: ${fechaInicio.value} a ${fechaFinal.value}\n\n",
            FontFactory.getFont("arial", 22f, Font.BOLD, BaseColor.BLACK)
        )
        documento.add(titulo)

        val ventas = Paragraph(
            "VENTAS\n\n",
            FontFactory.getFont("arial", 22f, Font.BOLD, BaseColor.BLACK)
        )
        documento.add(ventas)

        val tabla = PdfPTable(3)
        tabla.addCell("FECHA VENTA")
        tabla.addCell("ID VENTA")
        tabla.addCell("TOTAL VENTA")

        val datos = viewModel.listaVentas.value

        //Log.d("DATOS PDF", datos.toString())

        datos.forEach {
            tabla.addCell(it.fechaVenta.substring(0 .. 9))
            tabla.addCell(it.idVenta)
            tabla.addCell(it.total.toString())
            totalVentasReporte += it.total
        }

        documento.add(tabla)

        val totalVentaRep = Paragraph(
            "\nTotal ventas reporte: $totalVentasReporte",
            FontFactory.getFont("arial", 12f, Font.BOLD, BaseColor.BLACK)
        )

        documento.add(totalVentaRep)

        documento.close()

    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: DocumentException) {
        e.printStackTrace()
    }
}