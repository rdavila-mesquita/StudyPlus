package com.example.study.activity

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.study.R
import com.example.study.viewModel.Assunto
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(
    onHomeClick: () -> Unit = {},
    onProgressMetaClick: () -> Unit = {},
    onLogout: () -> Unit = {},
    onAdd: () -> Unit = {},
    onAssuntoClick: (Assunto) -> Unit,
    onStatusChange: (Assunto, Boolean) -> Unit,
    assuntos: List<Assunto>
){
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BottomBar(onHomeClick = onHomeClick, onMetaClick = onProgressMetaClick) },
        floatingActionButton = { AddButton(onAdd) },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(bottom = 70.dp)) {
            Header(onLogout = {showLogoutDialog = true})
            Text(
                "O que você vai estudar hoje?",
                fontSize = 25.sp,
                lineHeight = 45.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E3F9D),
                modifier = Modifier.padding(20.dp)
            )
            AssuntoList(assuntos = assuntos, onMetaClick = onAssuntoClick, onStatusChange = onStatusChange)
        }
    }

    if(showLogoutDialog){
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false},
            title = { Text("Confirmar Logout")},
            text = { Text("Tem certeza que deseja sair?")},
            confirmButton = {
                TextButton(
                    onClick = {
                        onLogout()
                        showLogoutDialog = false
                    }
                ) {
                    Text("Sair", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false}
                )
                {
                    Text("Cancelar")
                }
            }
        )
    }
}






@Composable
fun Header(onLogout: () -> Unit){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2E3F9D))
            .padding(20.dp)
            .padding(top = 40.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                modifier = Modifier.height(30.dp)
            )

            Spacer(Modifier.height(10.dp))

            Text("Seja Bem Vindo",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Icon(
            painter = painterResource(R.drawable.ic_logout),
            contentDescription = "Sair",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(32.dp)
                .clickable(onClick = onLogout)
        )
    }
}

@Composable
fun AddButton(onAdd: () -> Unit){
    FloatingActionButton(
        onClick = onAdd,
        containerColor = Color(0xFF2F4BFF),
        shape = CircleShape,
        modifier = Modifier
            .size(110.dp)
            .offset(y = 70.dp)

    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = "Adicionar",
            tint = Color.White,
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun AssuntoList(
    assuntos: List<Assunto>,
    onMetaClick: (Assunto) -> Unit = {},
    onStatusChange: (Assunto, Boolean) -> Unit
){
    if (assuntos.isEmpty()){
    Text(
        "Nenhum assunto? Adicione e vamos estudar!",
        color = Color(0xFF2E3F9D),
        fontSize = 15.sp,
        modifier = Modifier.padding(vertical = 30.dp, horizontal = 20.dp)
    )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
            ) {
            items(assuntos){ assuntoItem ->
                AssuntoCard(
                    assunto = assuntoItem,
                    onMetaClick = { onMetaClick(assuntoItem) },
                    onStatusChange = { novoStatus ->
                        onStatusChange(assuntoItem, novoStatus)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun AssuntoCard(assunto: Assunto, onMetaClick: () -> Unit = {}, onStatusChange: (Boolean) -> Unit){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onMetaClick)
            .background(Color(0xFFDEDEDE), shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        if (!assunto.concluida) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF60C8E9))
            )
        }else {

            Box(
                modifier = Modifier
                    .width(8.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF6ABD00))
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = assunto.titulo,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.height(40.dp).offset(y = 10.dp)
            )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
//                .height(40.dp)
        ) {
            CheckBox(assunto = assunto, onStatusChange = onStatusChange)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
    val sampleAssuntos = listOf(
        Assunto(id = "1", titulo = "Estudar Jetpack Compose", concluida = false),
        Assunto(id = "2", titulo = "Revisar Coroutines", concluida = true)
    )

    Home(
        assuntos = sampleAssuntos,
        onAssuntoClick = {},
        onStatusChange = { _, _ -> } // Usa { _, _ -> } para uma função de dois parâmetros
    )
}
