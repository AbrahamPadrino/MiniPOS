package com.example.minipos.models

data class Proveedor(
    var email: String = "",
    var nomProveedor: String = "",
    var telefono: String = ""
) {
    override fun toString(): String {
        return nomProveedor
    }
}
