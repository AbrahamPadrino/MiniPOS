package com.example.minipos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.minipos.navigation.NavManager
import com.example.minipos.ui.theme.MiniPOSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniPOSTheme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    NavManager()

                }
            }
        }
    }
}