package com.example.study.activity


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.study.viewModel.Assunto

@Composable
fun ProgressoMetaScreen(
    assuntos: List<Assunto>,
    onHomeClick: () -> Unit = {},
    onProgressMetaClick: () -> Unit = {},
    onAdd: () -> Unit = {},
    onMetaClick: (Assunto) -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            BottomBar(onHomeClick = onHomeClick, onMetaClick = onProgressMetaClick)
        },
        floatingActionButton = { AddButton(onAdd) },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2E3F9D))
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "logo",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-50).dp)
                    .size(200.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 90.dp)
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                )
                {
                    Text(
                        "Meta Diária",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF2E3F9D),
                        modifier = Modifier
                            .padding(vertical = 25.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    if (assuntos.isEmpty()){
                        Text(
                            "Nenhum assunto? Adicone e vamos estudar!",
                            color = Color(0xFF2E3F9D),
                            fontSize = 15.sp,
                            modifier = Modifier.padding(vertical = 30.dp, horizontal = 20.dp)
                        )
                    } else {
                        AssuntoList(assuntos = assuntos, onMetaClick = onMetaClick)
                            }
                        }
                    }
                }
            }
        }


@Preview
@Composable
fun ProgressoMetaScreenPreview(){
    ProgressoMetaScreen(assuntos = emptyList())
}