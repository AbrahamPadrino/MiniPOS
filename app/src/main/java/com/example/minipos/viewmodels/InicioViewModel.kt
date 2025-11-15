package com.example.minipos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minipos.models.Usuario
import com.example.minipos.network.Retrofit
import com.example.minipos.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class InicioViewModel: ViewModel() {

    private var _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> get() = _isLogin

    private lateinit var response: Response<LoginResponse>

    fun login(usuario: Usuario) {
        viewModelScope.launch(Dispatchers.IO) {
            response = Retrofit.webService.login(usuario)
            withContext(Dispatchers.Main) {
                if (response.body()!!.codigo == "200") {
                    _isLogin.value = true
                } else {
                    _isLogin.value = false
                }
            }
        }
    }

    fun logout() {
        _isLogin.value = false
    }

    fun validarCampos(
        usuario: String,
        contrasena: String
    ): Boolean {
        return if(
            usuario.isEmpty() ||
            contrasena.isEmpty()
        ) {
            false
        } else {
            true
        }
    }
}