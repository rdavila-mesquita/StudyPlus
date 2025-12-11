package com.example.study.activity

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollable2DState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import com.example.study.R
import com.example.study.viewModel.Assunto

@Composable
fun AssuntoScreen(
    onDelete: () -> Unit = {},
    onEdit: () -> Unit = {},
    onFocus:() -> Unit = {},
    onHomeClick: () -> Unit = {},
    assunto: Assunto,
    onStatusChange: (Boolean) -> Unit
){
    
    var showDeleteDialog by remember { mutableStateOf(false) }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E3F9D))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 50.dp)
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
                        .clickable(onClick = onHomeClick)
                )

                Image(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .height(40.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF2E3F9D))
                )

                Card(
                    modifier = Modifier
                        .fillMaxHeight(0.83f)
                        .align(Alignment.CenterHorizontally)
                      //  .background(Color.Gray)
                        .padding(10.dp)
                        .width(300.dp),
                    colors = CardDefaults.cardColors(Color.White),
                   // border = BorderStroke(width = 2.dp, color = Color(0xFFC0C0C0))

                ) {
                    Text(
                        text = assunto.titulo,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E3F9D),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 10.dp)
                    )
                    Text(
                        text = assunto.descricao,
                        fontSize = 14.sp,
                        color = Color(0xFF2E3F9D),
                        textAlign = TextAlign.Left,

                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 10.dp, horizontal = 16.dp)
                            .verticalScroll(rememberScrollState())

                    )


                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(vertical = 10.dp)
                ) {
                    Text("Concluído:")
                    Checkbox(
                        checked = assunto.concluida,
                        onCheckedChange = { novoStatus ->
                            onStatusChange(novoStatus)
                        }

                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
//                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        IconButton(
                            onClick = onEdit,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_edit),
                                contentDescription = "icone para editar o card",
                                tint = Color(0xFF2E3F9D)
                            )
                        }
                        IconButton(
                            onClick = {showDeleteDialog = true},
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_delete),
                                contentDescription = "icone para deletar o card",
                                tint = Color(0xFF2E3F9D)
                            )
                        }
                    }

                    Button(
                        onClick = onFocus,
                        colors = ButtonDefaults.buttonColors(Color(0xFF60C8E9)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Iniciar Foco",
                            color = Color(0xFF2E3F9D))
                    }
                }

                if (showDeleteDialog){
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false},
                        title = {Text("Confirmar Exclusão")},
                        text = {Text("Tem certeza que deseja excluir este card?")},
                        confirmButton = {
                            TextButton( onClick = {
                                    onDelete()
                                    showDeleteDialog =false
                                }) {
                                Text("Excluir", color = Color.Red)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {showDeleteDialog = false}) {
                                Text("Cancelar")
                            }
                        }
                    )
                }

            }
        }
    }
}


@Preview
@Composable
fun MetaScreenPreview(){
    AssuntoScreen(
        assunto = com.example.study.viewModel.Assunto(
            id = "1",
            titulo = "Título da Meta",
            descricao = "Lorem IpsumIpsum is simply dummy text of the printinLorem Ipsum is simIpsum s Lorem IpsumIpsum is simply dummy text of the printinLorem Ipsum is simIpsum s simply dummy text of the printiLorem IpsumIpsum is simply dummy text of the printinLorem Ipsum is simIpsum s simply dummy text of the printiLorem IpsumIpsum is simply dummy text of the printinLorem Ipsum is simIpsum s simply dummy text of the printiLorem IpsumIpsum is simply dummy text of the printinLorem Ipsum is simIpsum s simply dummy text of the printiLorem IpsumIpsum is simply dummy text of the printinLorem Ipsum is simIpsum s simply dummy text of the printiLorem IpsumIpsum is simply dummy text of the printinLorem Ipsum is simIpsum s simply dummy text of the printisimply dummy text of the printinLorem Ipsum is simIpsum is simply dummy text of the printinLos simply dummy text of the printinLorem Ipsum is simIpsum is simply dummy text of the printinLos simply dummy text of the printinLorem Ipsum is simIpsums simply dummy text of the printinLorem Ipsum is simIpsum is simply dummy text of the printinLos simply dummy text of the printinLorem Ipsum is simIpsum is simply dummy text of the printinLos simply dummy text of the printinLorem Ipsum is simIpsum is simply dummy text of the printinLos simply dummy text of the printinLorem Ipsum is simIpsum is simply dummy text of the printinLos simply dummy text of the printinLorem Ipsum is simIpsum is simply dummy text of the printinLos simply dummy text of the printinLorem Ipsum is simIpsum is simply dummy text of the printinLo is simply dummy text of the printinLois simply dummy text of the printinLorem Ips printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            concluida = false
        ),
        onDelete = {},
        onEdit = {},
        onFocus = {},
        onHomeClick = {},
        onStatusChange = {_ ->}
    )
}