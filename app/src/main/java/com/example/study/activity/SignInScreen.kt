package com.example.study.activity


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.study.R

enum class AuthMode { SignIn, SignUp }

@Composable
fun SignInScreen(
    mode: AuthMode = AuthMode.SignIn,
    onPrimary: (String, String, String) -> Unit = {_, _, _ -> },
    onForgotPassword: () -> Unit = {},
    onSwitch: () -> Unit = {}
){
    val isSignUp = mode == AuthMode.SignUp

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var user by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val titleText = if (isSignUp) "Criar Conta" else "Entrar"
    val primaryText = if (isSignUp) "Criar Conta" else "Entrar"
    val bottomPrompt = if (isSignUp) "Já tem uma conta?" else "Não tem uma conta?"
    val bottomLink = if (isSignUp) "Entrar" else "Criar Conta"

    Box(modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding()
    ){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(50.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Descrição",
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
                    .padding(top = 50.dp),
                colorFilter = ColorFilter.tint(color = Color(0xFF2E3F9D))


            )

            Text(
                text = titleText,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 75.dp, bottom = 12.dp),
                textAlign = TextAlign.Center
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail")},
                leadingIcon = { Icon(painterResource(R.drawable.ic_email), null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.DarkGray,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = MaterialTheme.colorScheme.primary

                )
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha")},
                leadingIcon = { Icon(painterResource(R.drawable.ic_password), null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = {showPassword = !showPassword}) {
                        Text(if (showPassword) "Ocultar" else "Mostrar")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.DarkGray,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            if (isSignUp){
                TextField(
                    value = user,
                    onValueChange = { user = it },
                    label = { Text("Usuário")},
                    singleLine = true,
                    leadingIcon = { Icon(painterResource(R.drawable.ic_profile), null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.DarkGray,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(Modifier.height(40.dp))

                Button(
                    onClick = { onPrimary(email, password, user) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2F4BFF),
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) {
                    Text(primaryText, fontWeight = FontWeight.Bold)
                }
            }

            if(!isSignUp){
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 6.dp, bottom = 8.dp)
//                ){
//                    TextButton(
//                        onClick = onForgotPassword,
//                        modifier = Modifier.align(Alignment.CenterEnd),
//                        colors = ButtonDefaults.textButtonColors(
//                            contentColor = Color(0xFF2F4BFF)
//                        )
//                    ) {
//                        Text("Esqueci minha senha")
//                    }
//                }
//
                Spacer(Modifier.height(34.dp))

                Button(
                    onClick = { onPrimary(email, password, user) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2F4BFF),
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) {
                    Text(primaryText, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.weight(1f))

                val annotated = buildAnnotatedString {
                    append(bottomPrompt)
                    pushStringAnnotation("switch", "switch")
                    withStyle(SpanStyle(color = Color(0xFF4AC4E0), fontWeight = FontWeight.Bold))
                    { append(bottomLink) }
                    pop()
                }

                Text(
                    text = annotated,
                    color = Color.White,
                    modifier = Modifier
                        .padding(vertical = 18.dp)
                        .navigationBarsPadding()
                        .clickable(
                            onClick = { onSwitch() }
                        )
                )
            }
        }

    }



}

@Preview
@Composable
fun SignInPreview(){
    SignInScreen()
}