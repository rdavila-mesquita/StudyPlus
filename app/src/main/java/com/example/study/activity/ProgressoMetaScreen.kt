package com.example.study.activity


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.study.R
import com.example.study.viewModel.AppViewModel
import com.example.study.viewModel.Assunto
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ProgressoMetaScreen(
    assuntos: List<Assunto>,
    onHomeClick: () -> Unit = {},
    onProgressMetaClick: () -> Unit = {},
    onStatusChange: (Assunto, Boolean) -> Unit,
    onAdd: () -> Unit = {},
    onMetaClick: (Assunto) -> Unit = {},
    avm: AppViewModel
) {

    val horasInput by avm.totalTime.collectAsState()
    val isTimerRunning by avm.isTimerRunning.collectAsState()
    val currentTime by avm.currentTime.collectAsState()
    val timerProgress by avm.timerProgress.collectAsState()

    var horasInputText by remember { mutableStateOf((horasInput / 3600000).toString()) }

    LaunchedEffect(horasInput) {
        horasInputText = (horasInput / 3600000).toString()
    }

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
                    .offset(y = (-25).dp)
                    .size(150.dp)
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


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        contentAlignment = Alignment.Center

                    ){
                        TimerMeta(
                            progress = timerProgress,
                            currentTime = currentTime,
                            isTimerRunning = isTimerRunning,
                            onToggle = { avm.toggleTimer() },
                            onReset = { avm.resetarTimer() },
                            handleColor = Color(0xDD2E3F9D),
                            inactiveBarColor = Color(0xFF2E3F9D),
                            activeBarColor = Color.LightGray,
                            modifier = Modifier.size(200.dp)
                        )
                    }

                    OutlinedTextField(
                        value = horasInputText,
                        onValueChange = { novoValor ->
                            if (novoValor.all { it.isDigit() }){
                                horasInputText = novoValor
                                val horasLong = novoValor.toLongOrNull()
                                if (horasLong != null){
                                    avm.definirTempoTotal(horasLong)
                                }
                            }
                        },
                        label = { Text("Quantas horas você quer estudar?") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp, vertical = 10.dp)
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        "Assuntos concluídos:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = Color(0xFF2E3F9D),
                    )

                    if (assuntos.isEmpty()){
                        Text(
                            "Veja suas metas concluídas aqui.",
                            color = Color(0xFF2E3F9D),
                            fontSize = 15.sp,
                            modifier = Modifier.padding(vertical = 30.dp, horizontal = 20.dp)
                        )
                    } else {
                        AssuntoList(assuntos = assuntos, onMetaClick = onMetaClick, onStatusChange = onStatusChange)
                            }
                        }
                    }
                }
            }
        }

@Composable
fun TimerMeta(
    progress: Float,
    currentTime: Long,
    isTimerRunning: Boolean,
    onToggle: () -> Unit,
    onReset: () -> Unit,
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp
){
    var size by remember { mutableStateOf(IntSize.Zero) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .onSizeChanged {
                size = it
            }
    ){
        Canvas(modifier = modifier) {
            drawArc(
                color = inactiveBarColor,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            drawArc(
                color = activeBarColor,
                startAngle = 35f,
                sweepAngle = -250f * progress,
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            val center = Offset(size.width / 2f, size.height / 2f)
            val beta = (35f - 250f * progress) * (Math.PI / 180f).toFloat()
            val r = size.width / 2f
            val a = cos(beta) * r
            val b = sin(beta) * r
            drawPoints(
                listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Points,
                color = handleColor,
                strokeWidth = (strokeWidth * 3f).toPx(),
                cap = StrokeCap.Round
            )
        }
        
        val progressoPercentual = (1f - progress) * 100

            Text(
                text = "${progressoPercentual.toInt()}%",
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E3F9D)
            )

        Button(
            onClick = {
                if(currentTime <= 0L) {
                    onReset()
                } else {
                    onToggle()
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = when {
                    currentTime <= 0L -> Color(0xFF2E3F9D)
                    isTimerRunning -> Color(0xFF565656)
                    else -> Color(0xFF2E3F9D)
                }
            )
        ) {
            Text(
                text = when {
                    currentTime <= 0L -> "Restart"
                    isTimerRunning -> "Parar"
                    else -> "Iniciar"
                }
            )
        }

        }
    }



@Preview
@Composable
fun ProgressoMetaScreenPreview(){
    val avm: AppViewModel = viewModel()
    ProgressoMetaScreen(
        avm = avm,
        assuntos = emptyList(),
        onHomeClick = {},
        onProgressMetaClick = {},
        onStatusChange = {_,_ ->},
        onAdd = {},
    )
}