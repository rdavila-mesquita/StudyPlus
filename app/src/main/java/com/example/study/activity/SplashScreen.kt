package com.example.study.activity


import android.window.SplashScreen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen(
    onSingupClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Box{

            Image(
                painter = painterResource(com.example.study.R.drawable.ic_background),
                contentDescription = "imagem de fundo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(com.example.study.R.drawable.ic_logo),
                contentDescription = "logo",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(200.dp)
            )

        }

        Spacer(Modifier.height(200.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0xFF2E3F9D))
                    .size(height = 60.dp, width = 150.dp)

            ){
                Text(
                    text = "Entrar",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable(onClick = onLoginClick)
                )
            }


            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .border(BorderStroke(4.dp, Color(0xFF2E3F9D)), shape = MaterialTheme.shapes.large)
                    .size(height = 60.dp, width = 150.dp)

            ){
                Text(
                    text = "Criar Conta",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF2E3F9D),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable(onClick = onSingupClick)
                )
            }


        }
    }
}

@Preview
@Composable
fun SplashScreenPreview(){
    SplashScreen()
}