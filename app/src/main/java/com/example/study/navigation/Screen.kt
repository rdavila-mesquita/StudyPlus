package com.example.study.navigation

import com.example.study.viewModel.Assunto

sealed class Screen(val route: String) {
    data object SignIn : Screen("signin")
    data object SignUp : Screen("signup")
    data object Home : Screen("home")
    data object SplashScreen : Screen("splashscreen")
    object AssuntoScreen : Screen("assunto/{assuntoId}"){
        fun createRoute(assuntoId: String) = "assunto/$assuntoId"
    }
    data object AddAssunto : Screen("addassunto")
    object EditarAssunto : Screen("editarassunto/{assuntoId}?titulo={titulo}&assunto={assunto}"){
        fun argumentos(assunto: Assunto): String{
            val encodedTitulo = assunto.titulo.replace("/", "%2F")
            val encodedAssunto = assunto.descricao.replace("/", "%2F")
            return "editarassunto/${assunto.id}?titulo=$encodedTitulo&assunto=$encodedAssunto"
        }
    }
    data object ProgressoMetaScreen : Screen("progressometa")
    data object PomodoroScreen : Screen("pomodoro")

}