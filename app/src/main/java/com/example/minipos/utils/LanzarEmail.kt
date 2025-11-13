package com.example.minipos.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class LanzarEmail {
    fun lanzarEmail(
        context: Context,
        email: String
    ) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }

        context.startActivity(intent)
    }
}