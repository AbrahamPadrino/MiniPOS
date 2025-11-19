package com.example.minipos.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minipos.models.DatosGrafica
import com.example.minipos.models.DatosVenta
import com.example.minipos.network.Retrofit
import com.example.minipos.response.VentaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ReporteViewModel: ViewModel() {

    private var _listaVentas = MutableStateFlow<List<DatosVenta>>(emptyList())
    val listaVentas = _listaVentas.asStateFlow()

    private var _datosGraficar = MutableStateFlow<List<DatosGrafica>>(emptyList())
    val datosGraficar = _datosGraficar.asStateFlow()

    private lateinit var response: Response<VentaResponse>

    fun obtenerPeriodoVentas(
        fechaInicio: String,
        fechaFinal: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            response = Retrofit.webService.obtenerVentasPeriodo(fechaInicio, fechaFinal)
            withContext(Dispatchers.Main) {
                if (response.body()!!.codigo == "200") {
                    _listaVentas.value = response.body()!!.resultado

                    val lista = response.body()!!.resultado.toMutableList()

                    _datosGraficar.value = emptyList()
                    val listaGraficaAux = _datosGraficar.value.toMutableList()

                    lista.forEach {
                        listaGraficaAux.add(
                            DatosGrafica(
                                etiqueta = it.fechaVenta,
                                valor = it.total.toFloat()
                            )
                        )
                    }

                    _datosGraficar.value = listaGraficaAux
                }
            }
        }
    }
}