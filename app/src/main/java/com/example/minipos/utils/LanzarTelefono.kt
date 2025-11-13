package com.example.minipos.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class LanzarTelefono {
    fun abrirTelefono(
        context: Context,
        telefono: String
    ) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$telefono")
        }

        context.startActivity(intent)
    }
}