package com.example.minipos.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minipos.models.ProductoVenta
import com.example.minipos.models.VentasSend
import com.example.minipos.network.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date

class VentaViewModel: ViewModel() {

    private var _listaProductos = MutableStateFlow<List<ProductoVenta>>(emptyList())
    val listaProductos = _listaProductos.asStateFlow()

    private var _totalVenta = MutableStateFlow(0.0)
    val totalVenta = _totalVenta.asStateFlow()

    fun validarCampo(codBar: String) {
        if (codBar.isEmpty()) {
            Log.d("VACIO", "codigo vacio")
        } else {
            obtenerProducto(codBar)
        }
    }

    fun obtenerProducto(codBar: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = Retrofit.webService.obtenerProducto(codBar)
            withContext(Dispatchers.Main) {
                if (response.body()!!.codigo == "200") {
                    if (response.body()!!.resultado.size > 0) {
                        val lista = _listaProductos.value.toMutableList()
                        lista.add(
                            ProductoVenta(
                                response.body()!!.resultado[0].almancen,
                                response.body()!!.resultado[0].codProducto,
                                response.body()!!.resultado[0].descripcion,
                                response.body()!!.resultado[0].nomProducto,
                                response.body()!!.resultado[0].nomProveedor,
                                response.body()!!.resultado[0].precio,
                                1
                            )
                        )

                        _listaProductos.value = lista

                        _totalVenta.value += response.body()!!.resultado[0].precio
                    }
                }
            }
        }
    }

    fun borrarProducto(index: Int) {
        val lista = _listaProductos.value.toMutableList()
        _totalVenta.value -= lista[index].precio
        lista.removeAt(index)
        _listaProductos.value = lista
    }

    fun registrarVenta() {
        var stringVenta = ""
        val tiempo = System.currentTimeMillis().toString()
        val fechaVenta = SimpleDateFormat("yyyy-MM-dd").format(Date())

        val lista = listaProductos.value.toMutableList()
        val listaVenta = ArrayList<ProductoVenta>(emptyList())

        lista.forEach { producto ->
            if (listaVenta.isEmpty()) {
                listaVenta.add(producto)
            } else {
                var existe = false
                listaVenta.forEach {
                    if (it.codProducto == producto.codProducto) {
                        it.cantidad += 1
                        existe = true
                    }
                }

                if(!existe) {
                    listaVenta.add(producto)
                    existe = false
                }
            }
        }

        listaVenta.forEach {
            if (stringVenta == "") {
                stringVenta = "${it.codProducto}_${it.cantidad}_${it.precio}"
            } else {
                stringVenta += "_${it.codProducto}_${it.cantidad}_${it.precio}"
            }
        }

        val datosSend = VentasSend(
            tiempo,
            stringVenta,
            fechaVenta,
            totalVenta.value
        )

        viewModelScope.launch(Dispatchers.IO) {
            val response = Retrofit.webService.agregarVenta(datosSend)
            withContext(Dispatchers.Main) {
                if (response.body()!!.codigo == "200") {
                    _listaProductos.value = emptyList()
                    _totalVenta.value = 0.0
                }
            }
        }
    }
}