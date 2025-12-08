package com.example.study.navigation
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object SignIn : Screen("signin")
    data object SignUp : Screen("signup")
    data object Home : Screen("home")
    data object SplashScreen : Screen("splashscreen")
    data object CriarMeta : Screen("criarmeta")
    data object MetaScreen : Screen("meta")
}