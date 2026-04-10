package me.alejandromoreno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val myNavController = rememberNavController()

            NavHost(
                navController = myNavController,
                startDestination = "Register",
                modifier = Modifier.fillMaxSize()
            ) {
               composable(route = "login"){
                   LoginScreen(onLoginSuccess = {}, onNavigateToRegister = {})
               }
                composable(route = "register")
                {
                    RegisterScreen(onRegisterSuccess = {}, onNavigateToLogin ={})
                }
            }
                }
            }
        }
