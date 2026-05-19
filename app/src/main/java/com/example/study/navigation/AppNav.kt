package com.example.study.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.study.activity.AddAssunto
import com.example.study.activity.AuthMode
import com.example.study.activity.Home
import com.example.study.activity.AssuntoScreen
import com.example.study.activity.Pomodoro
import com.example.study.activity.SplashScreen
import com.example.study.activity.ProgressoMetaScreen
import com.example.study.activity.SignInScreen
import com.example.study.activity.SetMode
import com.example.study.viewModel.AppViewModel
import com.example.study.viewModel.AuthViewModel

@Composable
fun AppNav(
    vm: AuthViewModel = viewModel(),
    avm: AppViewModel = viewModel()
) {
    val nav = rememberNavController()
    val state by vm.state.collectAsState()

    NavHost(nav, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                onLoginClick = { nav.navigate(Screen.SignIn.route) },
                onSingupClick = { nav.navigate(Screen.SignUp.route) }
            )
        }

        composable(Screen.SignIn.route) {
            SignInScreen(
                mode = AuthMode.SignIn,
                onPrimary = { email, password, user -> vm.signIn(email, password) },
                onForgotPassword = {},
                onSwitch = { nav.navigate(Screen.SignUp.route) }
            )
            LaunchedEffect(state.user) { if (state.user != null) gotoHome(nav) }
        }

        composable(Screen.SignUp.route) {
            SignInScreen(
                mode = AuthMode.SignUp,
                onPrimary = { email, pass, user -> vm.signUp(email, pass, user) },
                onForgotPassword = {},
                onSwitch = { nav.navigate(Screen.SignIn.route) }
            )
            LaunchedEffect(state.user) { if (state.user != null) gotoHome(nav) }
        }

        composable(Screen.Home.route) {
            LaunchedEffect(state.user?.uid) {
                if (state.user != null) avm.getAssuntos()
            }

            val assuntosNConcluidos by avm.assuntosNConcluidos.collectAsState()

            Home(
                onHomeClick = { nav.navigate(Screen.Home.route) },
                onProgressMetaClick = { nav.navigate(Screen.ProgressoMetaScreen.route) },
                onAdd = { nav.navigate(Screen.AddAssunto.route) },
                onLogout = {
                    vm.signOut()
                    nav.navigate(Screen.SplashScreen.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                },
                assuntos = assuntosNConcluidos,
                onAssuntoClick = { assunto ->
                    nav.navigate(Screen.AssuntoScreen.createRoute(assuntoId = assunto.id))
                },
                onStatusChange = { assunto, novoStatus ->
                    avm.atualizaStatus(assunto.id, novoStatus)
                }
            )
        }

        composable(Screen.AddAssunto.route){ backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val assunto = backStackEntry.arguments?.getString("assunto") ?: ""
            AddAssunto(
                mode = SetMode.Create,
                initialTitulo = titulo,
                initialAssunto = assunto,
                onSaveAssunto = { titulo, assunto ->
                    val userId = state.user?.uid
                    avm.saveAssunto(titulo, assunto, userId)
                    nav.popBackStack()
                },
                onHomeClick = { nav.navigate(Screen.Home.route) },
                onProgressMetaClick = { nav.navigate(Screen.ProgressoMetaScreen.route) }
            )
        }

        composable(
            Screen.EditarAssunto.route,
            arguments = listOf(
                navArgument("assuntoId") { type = NavType.StringType },
                navArgument("titulo") { type = NavType.StringType; nullable = true },
                navArgument("assunto") { type = NavType.StringType; nullable = true }
            )
        ){ backStackEntry ->
            val assuntoId = backStackEntry.arguments?.getString("assuntoId") ?: ""
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val assunto = backStackEntry.arguments?.getString("assunto") ?: ""

            AddAssunto(
                mode = SetMode.Edit,
                initialTitulo = titulo,
                initialAssunto = assunto,
                onHomeClick = { nav.navigate(Screen.Home.route) },
                onSaveAssunto = { novoTitulo, novoAssunto ->
                    avm.updateAssunto(novoTitulo,novoAssunto, assuntoId)
                    nav.popBackStack()
                },
                onProgressMetaClick = { nav.navigate(Screen.ProgressoMetaScreen.route) }
            )
        }

        composable(
            Screen.AssuntoScreen.route,
            arguments = listOf(
                navArgument("assuntoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val assuntoId = backStackEntry.arguments?.getString("assuntoId")

            LaunchedEffect(state.user?.uid, assuntoId){
                if (assuntoId != null){
                    avm.getAssuntoEspecifico(userId = state.user?.uid, assuntoId = assuntoId)
                }
            }

            DisposableEffect(Unit) {
                onDispose {
                    avm.limparAssuntoEspecifico()
                }
            }

            val assunto by avm.assuntoEspecifico.collectAsState()

            assunto?.let { assuntoDetalhe ->
                AssuntoScreen(
                    onDelete = {
                        avm.deleteAssunto(state.user?.uid, assuntoDetalhe.id)
                        nav.popBackStack()
                    },
                    onEdit = { nav.navigate(Screen.EditarAssunto.argumentos(assuntoDetalhe)) },
                    onFocus = { nav.navigate(Screen.PomodoroScreen.route) },
                    assunto = assuntoDetalhe,
                    onHomeClick = { nav.popBackStack() },
                    onStatusChange = { novoStatus ->
                        avm.atualizaStatus(assuntoDetalhe.id, novoStatus)
                    }
                )
            }
        }

        composable(Screen.ProgressoMetaScreen.route) {
            LaunchedEffect(state.user?.uid) {
                if (state.user != null) avm.getAssuntos()
            }

            val assuntosConcluidos by avm.assuntosConcluidos.collectAsState()
            ProgressoMetaScreen(
                onHomeClick = { nav.navigate(Screen.Home.route) },
                onProgressMetaClick = { nav.navigate(Screen.ProgressoMetaScreen.route) },
                onAdd = { nav.navigate(Screen.AddAssunto.route) },
                assuntos = assuntosConcluidos,
                onStatusChange = { assunto, novoStatus ->
                    avm.atualizaStatus(assunto.id, novoStatus)
                },
                avm = avm
            )
        }

        composable(Screen.PomodoroScreen.route) {
            Pomodoro(
                onExit = { nav.navigate(Screen.Home.route) }
            )
        }
    }
}

private fun gotoHome(nav: NavHostController) {
    nav.navigate(Screen.Home.route){
        popUpTo(0)
        launchSingleTop = true
    }
}