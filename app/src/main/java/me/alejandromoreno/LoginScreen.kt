package me.alejandromoreno

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.auth
import me.alejandromoreno.ui.theme.ExploraColombiaAppTheme

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    //elementos del video
    val auth = Firebase.auth
    val activity = LocalView.current.context as Activity
    var loginError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val primaryOrange = Color(0xFFE45D25)
    val lightGrayBg = Color(0xFFF8F9FE)
    val inputBg = Color(0xFFE5E5EA)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = lightGrayBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(primaryOrange),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Logo",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Explorando Colombia",
                        color = primaryOrange,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Bienvenido", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "Explora la magia de Colombia hoy.", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(text = "CORREO ELECTRÓNICO", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(28.dp)),
                    placeholder = { Text("nombre@ejemplo.com", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray) },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = inputBg,
                        unfocusedContainerColor = inputBg,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    supportingText = {
                        if (emailError.isNotEmpty()) {
                            Text(
                                text = emailError,
                                color = Color.Red
                            )
                        }
                    }, keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(text = "CONTRASEÑA", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Text(text = "¿Olvidaste tu contraseña?", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = primaryOrange, modifier = Modifier.clickable { })
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(28.dp)),
                    placeholder = { Text("........", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = inputBg,
                        unfocusedContainerColor = inputBg,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    supportingText = {
                        if (passwordError.isNotEmpty()){
                            Text(
                                text = passwordError,
                                color = Color.Red
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (loginError.isNotEmpty()){
                    Text(
                        loginError,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth().padding(bottom=8.dp)
                    )
                }

                Button(
                    onClick = {
                        //validacion
                        val isValidEmail: Boolean = validateEmail(email).first
                        val isValidPassword = validatePassword(password).first
                        emailError = validateEmail(email).second
                        passwordError = validatePassword(password).second
                        //onLoginSuccess()
                        if (isValidEmail && isValidPassword) {
                            // autenticacion firebase
                            auth.signInWithEmailAndPassword(email,password)
                                .addOnCompleteListener (activity){task ->
                                    if (task.isSuccessful){
                                        onLoginSuccess()
                                    }else{
                                        loginError = when(task.exception){
                                            is FirebaseAuthInvalidCredentialsException -> "Correo o contraseña incorrecta"
                                            is FirebaseAuthInvalidUserException -> "No existe una cuenta con este correo"
                                            else -> "Error al iniciar sesión. Intenta de nuevo"
                                        }
                                    }
                                }
                        }else{loginError = "Por favor ingrese un correo y contraseña válidos"}

                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(
                            brush = Brush.horizontalGradient(colors = listOf(primaryOrange, Color(0xFFFF8A65)))
                        ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Iniciar Sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    HorizontalDivider(modifier = Modifier.weight(1f), thickness = 0.5.dp, color = Color.LightGray)
                    Text(text = " O CONTINUAR CON ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    HorizontalDivider(modifier = Modifier.weight(1f), thickness = 0.5.dp, color = Color.LightGray)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SocialButton(text = "Google", modifier = Modifier.weight(1f), icon = Icons.Default.Email)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row {
                Text(text = "¿No tienes cuenta? ", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = "Regístrate",
                    color = primaryOrange,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToRegister() }
                )
            }
        }
    }
}

@Composable
fun SocialButton(text: String, modifier: Modifier = Modifier, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    OutlinedButton(
        onClick = { },
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ExploraColombiaAppTheme {
        LoginScreen(onLoginSuccess = {}, onNavigateToRegister = {})
    }
}