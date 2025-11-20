package com.example.minipos.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.minipos.R
import com.example.minipos.navigation.Views
import com.example.minipos.utils.Constantes
import kotlinx.coroutines.delay


@Composable
fun SplashView(
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        delay(Constantes.DURACION_SPLASH_SCREEN)
        navController.popBackStack()
        navController.navigate(Views.InicioView.route)
    }

    SplashView()
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SplashView() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GlideImage(
            model = R.drawable.dispositivos,
            contentDescription = "gif_logo"
        )
    }
}