package com.example.minipos.response

data class LoginResponse(
    val codigo: String,
    val mensaje: String,
    val resultado: String = ""
)