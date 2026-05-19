package com.example.study.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.study.R

enum class SetMode { Create, Edit }
@Composable
fun AddAssunto(
    mode: SetMode,
    onSaveAssunto: (String, String) -> Unit = { _, _ -> },
    onHomeClick: () -> Unit = {},
    onProgressMetaClick: () -> Unit = {},
    initialTitulo: String = "",
    initialAssunto: String = ""
) {
    val isEdit = mode == SetMode.Edit

    val screenTitle: String = if (!isEdit) "Novo Assunto" else "Editar Assunto"
    val buttonText: String = if (!isEdit) "Salvar" else "Atualizar"

    var titulo by remember { mutableStateOf(initialTitulo) }
    var assunto by remember { mutableStateOf(initialAssunto) }

    Scaffold(
        bottomBar = { BottomBar(onHomeClick = onHomeClick, onMetaClick = onProgressMetaClick) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2E3F9D))
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
                    .padding(top = 80.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(Color.White)

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {

                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = "icone para fechar",
                        tint = Color(0xFF60C8E9),
                        modifier = Modifier
                            .align(Alignment.End)
                            .size(40.dp)
                            .clickable(
                                onClick = onHomeClick
                            )
                    )

                    Image(
                        painter = painterResource(R.drawable.ic_logo),
                        contentDescription = "logo",
                        modifier = Modifier
                            .height(60.dp),
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color(0xFF2E3F9D))
                    )

                    Text(
                        text = screenTitle,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E3F9D),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 10.dp)
                    )


                        OutlinedTextField(
                            value = titulo,
                            onValueChange = { titulo = it },
                            label = { Text("Titulo") },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 10.dp, horizontal = 20.dp)
                        )

                        OutlinedTextField(
                            value = assunto,
                            onValueChange = { assunto = it },
                            label = { Text("Assunto") },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .height(300.dp)
                        )

                        Button(
                            onClick = { onSaveAssunto(titulo, assunto) },
                            colors = ButtonDefaults.buttonColors(Color(0xFF415DFD)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 20.dp)
                                .fillMaxWidth(0.8f)
                        ) {
                            Text(text = buttonText)
                        }
                }
            }
        }
    }
}

    @Composable
    fun BottomBar(
        onHomeClick: () -> Unit = {},
        onMetaClick: () -> Unit = {}
    ) {
        BottomAppBar(
            containerColor = Color(0xFF2E3F9D),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_home),
                    contentDescription = "icone para tela home",
                    tint = Color(0xFF415DFD),
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(
                            onClick = onHomeClick
                        )
                )


                IconButton(
                    onClick = onMetaClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_meta),
                        contentDescription = "icone para tela de meta",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }



@Preview
@Composable
fun AddAssuntoPreview(){
    AddAssunto(mode = SetMode.Create)
}