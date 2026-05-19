package com.example.study.viewModel

import androidx.annotation.Keep
import java.util.Date

data class Assunto (
    val id: String = "",
    val titulo: String = "",
    val descricao: String = "",
    val concluida: Boolean = false,
    val data: Date = Date(),
)