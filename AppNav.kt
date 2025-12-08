package com.example.study.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.study.activity.AuthMode
import com.example.study.activity.CriarMeta
import com.example.study.activity.SplashScreen
import com.example.study.activity.MetaScreen
import com.example.study.activity.SignInScreen
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
                onLoginClick = { nav.navigate(Screen.Login.route) },
                onSingupClick = { nav.navigate(Screen.SignUp.route) }
            )
        }

        composable(Screen.SignIn.route) {
            SignInScreen(
                mode = AuthMode.SignIn,
                onPrimary = { email, password, user -> vm.signIn(email, password) },
                onForgotPassword = {},
                onSwitch = { nav.navigate(Screen.SignIn.route) }
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

        composable(Screen.CriarMeta.route){
            CriarMeta(
                onHomeClick = { nav.navigate(Screen.Home.route) },
                onSaveMeta = { titulo, assunto -> avm.saveMeta(titulo, assunto) },
                onMetaClick = { nav.navigate(Screen.MetaScreen.route) }
            )

        }

        composable(Screen.MetaScreen.route) {
            MetaScreen()
        }
    }
}

private fun gotoHome(nav: NavHostController) {
    nav.navigate(Screen.Home.route){
        popUpTo(0)
        launchSingleTop = true
    }
}