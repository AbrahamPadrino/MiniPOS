package com.example.minipos.views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.minipos.R
import com.example.minipos.models.Usuario
import com.example.minipos.navigation.Views
import com.example.minipos.viewmodels.InicioViewModel

@Composable
fun InicioView(
    navController: NavController,
    viewModel: InicioViewModel
) {
    InicioScreen(
        navController = navController,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("ResourceAsColor")
@Composable
fun InicioScreen(
    navController: NavController,
    viewModel: InicioViewModel
) {
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current

    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    viewModel.isLogin.observe(lifeCycleOwner) {
        if (it) {
            navController.popBackStack()
            navController.navigate(Views.MenuView.route)
            Toast.makeText(context, "Bienvenid@", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(R.color.gris_oscuro)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .padding(40.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column {

                GlideImage(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    model = R.drawable.user_card_1,
                    contentDescription = null
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(40.dp, 12.dp),
                    value = usuario,
                    onValueChange = {
                        usuario = it
                    },
                    label = {
                        Text(text = "Usuario")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(40.dp, 12.dp),
                    value = contrasena,
                    onValueChange = {
                        contrasena = it
                    },
                    label = {
                        Text(text = "Contraseña")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Button(
                    modifier = Modifier
                        .padding(40.dp, 12.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = {

                        val isValido = viewModel.validarCampos(usuario, contrasena)
                        if (!isValido) {
                            Toast.makeText(context, "Debes llenar todos los campos", Toast.LENGTH_LONG).show()
                        } else {
                            viewModel.login(
                                Usuario(
                                    usuario = usuario,
                                    contrasena = contrasena
                                )
                            )
                        }
                    }
                ) {
                    Text(text = "Iniciar Sesión")
                }
            }
        }
    }
}