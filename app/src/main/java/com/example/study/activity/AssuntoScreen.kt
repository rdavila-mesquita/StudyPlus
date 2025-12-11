package com.example.study.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E3F9D))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 80.dp)
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
                        .height(60.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF2E3F9D))
                )

                Card(
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .align(Alignment.CenterHorizontally)
                        .padding(20.dp)
                        .width(300.dp)
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
                        fontSize = 20.sp,
                        color = Color(0xFF2E3F9D),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 10.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("Concluído:")
                        Checkbox(
                            checked = assunto.concluida,
                            onCheckedChange = { novoStatus ->
                                onStatusChange(novoStatus)
                            }

                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
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
                            onClick = onDelete,
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

            }
        }
    }
}


@Preview
@Composable
fun MetaScreenPreview(){
    AssuntoScreen(
        assunto = Assunto(
            id = "1",
            titulo = "Título da Meta",
            descricao = "Assunto de exemplo",
            concluida = false
        ),
        onDelete = {},
        onEdit = {},
        onFocus = {},
        onHomeClick = {},
        onStatusChange = {_ ->}
    )
}