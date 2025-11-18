package com.example.minipos.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minipos.models.Producto
import com.example.minipos.models.Proveedor
import com.example.minipos.network.Retrofit
import com.example.minipos.response.ProductoResponse
import com.example.minipos.response.ProveedorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class ProductoViewModel: ViewModel() {

    private var _listaProductos = MutableStateFlow<List<Producto>>(emptyList())
    val listaProductos = _listaProductos.asStateFlow()

    private var _listaProveedores = MutableStateFlow<List<Proveedor>>(emptyList())
    val listaProveedodes = _listaProveedores.asStateFlow()

    private lateinit var response: Response<ProductoResponse>
    private lateinit var responseProv: Response<ProveedorResponse>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            response = Retrofit.webService.obtenerProductos()
            withContext(Dispatchers.Main) {
                if (response.body()!!.codigo == "200") {
                    _listaProductos.value = response.body()!!.resultado
                }
            }
        }
    }

    fun obtenerProductos() {
        viewModelScope.launch(Dispatchers.IO) {
            response = Retrofit.webService.obtenerProductos()
            withContext(Dispatchers.Main) {
                if (response.body()!!.codigo == "200") {
                    _listaProductos.value = response.body()!!.resultado
                }
            }
        }
    }

    fun agregarProducto(producto: Producto) {
        viewModelScope.launch(Dispatchers.IO) {
            response = Retrofit.webService.agregarProducto(producto)
            //Log.d("ADD PRODUCTO", response.body().toString())
            withContext(Dispatchers.Main) {
                _listaProductos.value = Retrofit.webService.obtenerProductos().body()!!.resultado
            }
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch(Dispatchers.IO) {
            response = Retrofit.webService.actualizarProducto(producto.codProducto, producto)
            //Log.d("ADD PRODUCTO", response.body().toString())
            withContext(Dispatchers.Main) {
                _listaProductos.value = Retrofit.webService.obtenerProductos().body()!!.resultado
            }
        }
    }

    fun borrarProducto(codProducto: String) {
        viewModelScope.launch(Dispatchers.IO) {
            response = Retrofit.webService.borrarProducto(codProducto)
            //Log.d("PRODUCTOS", response.body().toString())
            withContext(Dispatchers.Main) {
                if (response.body()!!.codigo == "200") {
                    _listaProductos.value = _listaProductos.value.filter {
                        it.codProducto != codProducto
                    }
                }
            }
        }
    }

    fun obtenerProveedores() {
        viewModelScope.launch(Dispatchers.IO) {
            responseProv = Retrofit.webService.obtenerProveedores()
            withContext(Dispatchers.Main) {
                if (responseProv.body()!!.codigo == "200") {
                    _listaProveedores.value = responseProv.body()!!.resultado
                }
            }
        }
    }

    fun validarProducto(producto: Producto): Boolean {
        return if(
            producto.codProducto.isNotEmpty() &&
            producto.nomProducto.isNotEmpty() &&
            producto.descripcion.isNotEmpty() &&
            !producto.precio.isNaN()
        ) {
            true
        } else {
            false
        }
    }
}