package me.alejandromoreno

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    val auth = Firebase.auth
    val user = auth.currentUser

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "HOME SCREEN", fontSize = 30.sp)

            Spacer(modifier = Modifier.height(16.dp))

            if (user != null) {
                // mostrar correo usuario
                Text(text = user.email.toString(), fontSize = 18.sp, color = Color.Gray)
            } else {
                Text(text = "No hay usuario", color = Color.Red)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // boton para cerrar sesion
            Button(
                onClick = {
                    auth.signOut() // 1. Cierra sesión en Firebase
                    onLogout()      // 2. Avisa al NavHost para que nos mande al Login
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("Cerrar Sesión", color = Color(0xFFFF9900)) // El color naranja de tu imagen
            }
        }
    }
}