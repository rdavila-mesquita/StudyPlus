package com.example.study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.study.ui.theme.StudyTheme
import kotlinx.coroutines.delay

import android.media.MediaPlayer
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode


// NECESSÁRIO para "by remember { mutableStateOf() }"
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

// ------------------------------------

@Preview(
    showBackground = true,
    widthDp = 800,
    heightDp = 400
)

@Composable
fun PomodoroPreview() {
    StudyTheme {
        Pomodoro()
    }
}

@Composable
fun Pomodoro(onExit: () -> Unit = {}) {

    val isPreview = LocalInspectionMode.current

    val context = LocalContext.current

    val focusTime = 2 * 60  // 1500 segundos (25 min)
    val breakTime = 1 * 60   // 300 segundos (5 min)

    var timeLeft by remember { mutableStateOf(focusTime) }  // Inicia com foco
    var isRunning by remember { mutableStateOf(false) }
    var mode by remember { mutableStateOf("FOCUS") }
    var totalFocusTime by remember { mutableStateOf(0) }
    var cycleCount by remember { mutableStateOf(1) }  // Contador de ciclos

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isMusicPlaying by remember { mutableStateOf(false) }

    if (!isPreview){
        DisposableEffect(Unit) {
            mediaPlayer = MediaPlayer.create(context, R.raw.focus_music)
            mediaPlayer?.isLooping = true

            onDispose {
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }

    }



    LaunchedEffect(isRunning, totalFocusTime) {
        //onTotalFocusTime(totalFocusTime)
        if (isRunning)
            while (isRunning && timeLeft > 0) {
                delay(1000)
                timeLeft--

                if (timeLeft == 0) {
                    if (mode == "FOCUS") {
                        mode = "BREAK"
                        timeLeft = breakTime
                    } else {
                        mode = "FOCUS"
                        timeLeft = focusTime  // Correção: reinicia foco
                        cycleCount++  // Incrementa ciclo após descanso
                    }
                }
            }
    }

    // Layout principal (Box com flip numbers e botões)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Título da fase no topo
        Text(
            text = if (mode == "FOCUS") "Foco (Ciclo $cycleCount)" else "Descanso",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = if (mode == "FOCUS") Color.Gray else Color.Gray,  // Feedback visual
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 25.dp)
        )

        // Flip numbers no centro
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            val minutes = String.format("%02d", timeLeft / 60)
            val seconds = String.format("%02d", timeLeft % 60)
            FlipNumber(number = minutes)
            Spacer(modifier = Modifier.width(15.dp))
            FlipNumber(number = seconds)
        }

        // Botões na lateral direita
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            RoundButton(Icons.Default.MusicNote) {
                if(isMusicPlaying){
                    mediaPlayer?.pause()
                    isMusicPlaying = false
                }else{
                    mediaPlayer?.start()
                    isMusicPlaying = true
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            RoundButton(
                icon = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow
            ) {
                isRunning = !isRunning
            }
            Spacer(modifier = Modifier.height(16.dp))

            RoundButton(Icons.Default.Refresh) {
                // Reset para foco
                timeLeft = focusTime
                mode = "FOCUS"
                isRunning = false
                cycleCount = 1
                totalFocusTime = 0
            }

            Spacer(modifier = Modifier. height(16.dp))

            RoundButton(Icons.Default.Close) {
                onExit()
            }
        }
    }
}

//fun formatTime(time: Int): String {
//    val minutes = time / 60
//    val seconds = time % 60
//    return "%02d:%02d".format(minutes, seconds)
//}

@Composable
fun FlipNumber(number: String) {
    Box(
        modifier = Modifier
            .size(width = 280.dp, height = 260.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF111111)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number,
            color = Color(0xFF777777),
            fontSize = 200.sp
        )
    }
}

@Composable
fun RoundButton(icon: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(55.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF1A1A1A))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF7D7D7D),
            modifier = Modifier.size(28.dp)
        )
    }
}