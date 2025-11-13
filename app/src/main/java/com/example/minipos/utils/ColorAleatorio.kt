package com.example.minipos.utils

import androidx.compose.ui.graphics.Color

class ColorAleatorio {
    fun colorAleatorio(): Color {
        var colors = mutableListOf(
            Color(0XFFF44336),
            Color(0xFFE91E63),
            Color(0xFF9C27B0),
            Color(0xFF673AB7),
            Color(0xFF3F51B5),
            Color(0xFF2196F3),
            Color(0xFF009688),
            Color(0xFF4CAF50),
            Color(0xFFCDDC39),
            Color(0xFFFF9800),
            Color(0xFFFF5722)
        )

        val randomNumber = (Math.random() * colors.size).toInt()

        val color = colors[randomNumber]

        colors.removeAt(randomNumber)

        return color
    }
}