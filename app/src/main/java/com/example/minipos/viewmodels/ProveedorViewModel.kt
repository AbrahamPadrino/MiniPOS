package com.example.minipos.viewmodels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minipos.models.Proveedor
import com.example.minipos.network.Retrofit
import com.example.minipos.response.ProveedorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProveedorViewModel: ViewModel() {

    private var _listaProveedores = MutableStateFlow<List<Proveedor>>(emptyList())
    val listaProveedores = _listaProveedores.asStateFlow()

    private lateinit var response: Response<ProveedorResponse>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            response = Retrofit.webService.obtenerProveedores()
            //Log.d("PROVEEDORES", response.body().toString())
            withContext(Dispatchers.Main) {
                if (response.body()!!.codigo == "200") {
                    _listaProveedores.value = response.body()!!.resultado
                }
            }
        }
    }

    fun obtenerProveedores() {
        viewModelScope.launch(Dispatchers.IO) {
            response = Retrofit.webService.obtenerProveedores()
            //Log.d("PRODUCTOS", response.body().toString())
            withContext(Dispatchers.Main) {
                if (response.body()!!.codigo == "200") {
                    _listaProveedores.value = response.body()!!.resultado
                }
            }
        }
    }

    fun agregarProveedor(proveedor: Proveedor) {
        viewModelScope.launch(Dispatchers.IO) {
            response = Retrofit.webService.agregarProveedor(proveedor)
            //Log.d("PRODUCTOS", response.body().toString())
            withContext(Dispatchers.Main) {
                if (response.body()!!.codigo == "200") {
                    obtenerProveedores()
                }
            }
        }
    }

    fun editarProveedor(proveedor: Proveedor) {
        viewModelScope.launch(Dispatchers.IO) {
            response = Retrofit.webService.actualizarProveedor(proveedor.nomProveedor, proveedor)
            //Log.d("PRODUCTOS", response.body().toString())
            withContext(Dispatchers.Main) {
                if (response.body()!!.codigo == "200") {
                    obtenerProveedores()
                }
            }
        }
    }

    fun validarCampos(
        proveedor: MutableState<Proveedor>
    ): Boolean {
        return if (
            proveedor.value.nomProveedor.isEmpty() ||
            proveedor.value.email.isEmpty() ||
            proveedor.value.telefono.isEmpty()
        ) {
            false
        } else {
            true
        }
    }

}