package com.example.study

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.study.ui.theme.StudyTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyTheme {
                HomeScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudyTheme {
        HomeScreen()
    }
}

data class Assunto(
    val titulo: String,
    val data: String,
    val corBarra: Color
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    val assuntos = listOf(
        Assunto("Assunto 01", "27/11/2025", Color(0xFF2F4BFF)),
        Assunto("Assunto 02", "18/11/2025", Color(0xFF4AC4E0)),
        Assunto("Assunto 03", "10/09/2025", Color(0xFF2F4BFF)),
        Assunto("Assunto 04", "20/08/2025", Color(0xFF4AC4E0)),
        Assunto("Assunto 05", "15/08/2025", Color(0xFF2F4BFF)),
        Assunto("Assunto 06", "12/07/2025", Color(0xFF4AC4E0)),
        Assunto("Assunto 06", "12/07/2025", Color(0xFF4AC4E0)),
        Assunto("Assunto 06", "12/07/2025", Color(0xFF4AC4E0)),
        Assunto("Assunto 06", "12/07/2025", Color(0xFF4AC4E0)),
        Assunto("Assunto 06", "12/07/2025", Color(0xFF4AC4E0)),
        Assunto("Assunto 06", "12/07/2025", Color(0xFF4AC4E0)),
    )

    Scaffold(
        bottomBar = { BottomBar() },
        floatingActionButton = { AddButton() },
        floatingActionButtonPosition = FabPosition.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)

        ) {
            Header()

            AssuntosList(assuntos = assuntos)




        }
    }
}

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2D3DBF))
            .padding(20.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.studylogo),
                contentDescription = "Descrição",
                modifier = Modifier.size(48.dp)
            )
            Text("Seja Bem Vindo",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Sair",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(32.dp)
                .clickable(onClick = {})
        )
    }
}

@Composable
fun AddButton() {
    FloatingActionButton(
        onClick = { },
        containerColor = Color(0xFF2F4BFF),
        shape = CircleShape,
        modifier = Modifier
            .size(90.dp)
            .offset(y = 70.dp)

    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Adicionar",
            tint = Color.White,
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun BottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2D3DBF))
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = {}
        ) {
            Icon(

                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)


            )
        }



        Spacer(modifier = Modifier.width(80.dp)) // espaço para o FAB flutuante

        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Config",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )

        }

    }
}

@Composable
fun AssuntoCard(item: Assunto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {})
            .background(Color(0xFFDEDEDE), shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Box(
            modifier = Modifier
                .width(8.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(item.corBarra)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = item.titulo,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = item.data,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun AssuntosList(assuntos: List<Assunto>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),  // Ocupa toda a tela ou container
        contentPadding = PaddingValues(16.dp)  // Espaçamento interno
    ) {
        items(assuntos) { assunto ->  // Itera sobre a lista
            AssuntoCard(item = assunto)  // Renderiza o card para cada item
            Spacer(modifier = Modifier.height(8.dp))  // Espaçamento entre cards
        }
    }
}


