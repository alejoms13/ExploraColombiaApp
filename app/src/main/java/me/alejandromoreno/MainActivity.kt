package me.alejandromoreno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import me.alejandromoreno.ui.theme.ExploraColombiaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExploraColombiaAppTheme {
                val myNavController = rememberNavController()
                val auth = Firebase.auth
                val currentUser = auth.currentUser
                var myStartDestination by remember { mutableStateOf("login") }

                if (currentUser != null) {
                    myStartDestination = "home"
                } else {
                    myStartDestination = "login"
                }
                NavHost(
                    navController = myNavController,
                    startDestination = myStartDestination,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(route = "login") {
                        LoginScreen(
                            onLoginSuccess = {
                                myNavController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onNavigateToRegister = {
                                myNavController.navigate("register")
                            }
                        )
                    }
                    composable(route = "register") {
                        RegisterScreen(
                            onRegisterSuccess = {
                                myNavController.navigate("home") {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            onNavigateToLogin = {
                                myNavController.navigate("login")
                            },
                            onBackClick = {
                                myNavController.popBackStack()
                            }
                        )
                    }

                    composable(route = "home") {
                        HomeScreen(
                            onLogout = {
                                myNavController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}