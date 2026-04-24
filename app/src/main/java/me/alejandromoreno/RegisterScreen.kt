package me.alejandromoreno

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import me.alejandromoreno.ui.theme.ExploraColombiaAppTheme


@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    //todos los estados
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(false) }

    val primaryOrange = Color(0xFFE45D25)
    val lightGrayBg = Color(0xFFF8F9FE)
    val inputBg = Color(0xFFE5E5EA)

    //el video de validacion de register
    val auth = Firebase.auth
    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var passwordConfirmationError by remember { mutableStateOf("") }
    var registerError by remember { mutableStateOf("") } // Para errores generales de Firebase



    Surface(
        modifier = modifier.fillMaxSize(),
        color = lightGrayBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.Start).offset(x = (-12).dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = primaryOrange
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Explorando Colombia",
                color = primaryOrange,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Crea tu cuenta",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "Empieza tu aventura por el realismo mágico",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp).align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                RegisterField(
                    label = "NOMBRE COMPLETO",
                    value = name,
                    onValueChange = { name = it; nameError = "" },
                    error = nameError, // <-- añadir esto
                    placeholder = "Tu nombre",
                    leadingIcon = Icons.Default.Person,
                    inputBg = inputBg
                )
                Spacer(modifier = Modifier.height(20.dp))
                RegisterField(
                    label = "CORREO ELECTRÓNICO",
                    value = email,
                    onValueChange = { email = it; emailError = "" },
                    error = emailError, // <-- añadir esto
                    placeholder = "hola@ejemplo.com",
                    leadingIcon = Icons.Default.Email,
                    inputBg = inputBg
                )
                Spacer(modifier = Modifier.height(20.dp))
                RegisterField(
                    label = "CONTRASEÑA",
                    value = password,
                    onValueChange = { password = it; passwordError = "" },
                    error = passwordError, // <-- añadir esto
                    placeholder = "........",
                    leadingIcon = Icons.Default.Lock,
                    inputBg = inputBg,
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(20.dp))
                RegisterField(
                    label = "CONFIRMAR",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it; passwordConfirmationError = "" },
                    error = passwordConfirmationError, // <-- añadir esto
                    placeholder = "........",
                    leadingIcon = Icons.Default.Refresh,
                    inputBg = inputBg,
                    isPassword = true
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Checkbox(
                    checked = acceptedTerms,
                    onCheckedChange = { acceptedTerms = it },
                    colors = CheckboxDefaults.colors(checkedColor = primaryOrange)
                )
                Text(
                    text = buildAnnotatedString {
                        append("Acepto los ")
                        withStyle(style = SpanStyle(color = primaryOrange, fontWeight = FontWeight.Bold)) {
                            append("términos y condiciones")
                        }
                        append(" así como el tratamiento de datos personales.")
                    },
                    fontSize = 12.sp,
                    color = Color.Gray,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (registerError.isNotEmpty()) {
                Text(
                    text = registerError,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 12.dp),
                    textAlign = TextAlign.Center
                )
            }

            Button(
                onClick = { //onRegisterSuccess()

                    val nameRes = validateName(name)
                    val emailRes = validateEmail(email)
                    val passwordRes = validatePassword(password)
                    val confirmRes = validateConfirmPassword(password, confirmPassword)


                    nameError = nameRes.second
                    emailError = emailRes.second
                    passwordError = passwordRes.second
                    passwordConfirmationError = confirmRes.second


                    val isFormValid = nameRes.first && emailRes.first && passwordRes.first && confirmRes.first

                    if (isFormValid) {
                        if (acceptedTerms) {
                            // crear usuario en firebase
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        //si todo esta ok, pues el usuario vuelve a login para poder volver a iniciar sesion
                                        onRegisterSuccess()
                                    } else {
                                        registerError = task.exception?.localizedMessage ?: "Error desconocido al registrar"
                                    }
                                }
                        } else {
                            registerError = "Debes aceptar los términos y condiciones para continuar."
                        }
                    } else {
                        registerError = "Por favor, corrige los errores en el formulario."
                    }
                },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(32.dp),
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
                        Text("Registrarse", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(24.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(modifier = Modifier.weight(1f), thickness = 0.5.dp, color = Color.LightGray)
                Text(text = " O REGÍSTRATE CON ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                HorizontalDivider(modifier = Modifier.weight(1f), thickness = 0.5.dp, color = Color.LightGray)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SocialButton(text = "Google", modifier = Modifier.weight(1f), icon = Icons.Default.Email)
                SocialButton(text = "Apple", modifier = Modifier.weight(1f), icon = Icons.Default.Lock)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(text = "¿Ya tienes una cuenta? ", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = "Inicia sesión",
                    color = primaryOrange,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }
    }
}

@Composable
fun RegisterField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    inputBg: Color,
    modifier: Modifier = Modifier,
    error: String = "", // Ya lo tenías, ahora lo usaremos
    isPassword: Boolean = false
) {
    Column(modifier = modifier) {
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(28.dp)),
            placeholder = { Text(placeholder, color = Color.Gray) },
            leadingIcon = { Icon(leadingIcon, contentDescription = null, tint = Color.Gray) },
            // se mostrara el erro debajo de lcampo
            isError = error.isNotEmpty(),
            supportingText = {
                if (error.isNotEmpty()) {
                    Text(text = error, color = Color.Red, fontSize = 11.sp)
                }
            },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,   // Para que se vea el texto
                unfocusedTextColor = Color.Black, // Para que se vea el texto
                focusedContainerColor = inputBg,
                unfocusedContainerColor = inputBg,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent // Quitamos la línea roja fea de abajo
            ),
            singleLine = true,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ExploraColombiaAppTheme {
        RegisterScreen(onRegisterSuccess = {}, onNavigateToLogin = {})
    }
}