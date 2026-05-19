package com.example.study.viewModel


import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AuthUiState(
    val isloading: Boolean = false,
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser,
    val error: String? = null
)

class AuthViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _uiState = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _uiState

    fun signIn(email: String, password: String) {
        setLoading()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    ok()
                } else {
                    fail(task.exception)
                }
            }
    }

    fun signUp(email: String, password: String, user: String){
        setLoading()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    ok()
                } else {
                    fail(task.exception)
                }
            }
    }

    fun signOut(){
        auth.signOut()
        _uiState.value = AuthUiState(user = null)
    }

    private fun setLoading(){
        _uiState.value = _uiState.value.copy(isloading = true, error = null)
    }
    private fun ok() {
        _uiState.value = _uiState.value.copy(isloading = true, user = auth.currentUser, error = null)
    }

    private fun fail(ex: Exception?) {
        _uiState.value = _uiState.value.copy(isloading = true, user = auth.currentUser, error = mapError(ex))
    }

    private fun mapError(ex: Exception?): String {
        val e = ex ?: return "Falha na autenticação. Por favor, tente novamente."
        return when (e){
            is FirebaseAuthUserCollisionException -> "Esta conta já existe. Tente entrar ou redefinir sua senha."
            is FirebaseAuthInvalidCredentialsException ->
                when (e.errorCode) {
                    "ERROR_INVALID_EMAIL" -> "Endereço de e-mail inválido."
                    "ERROR_WRONG_PASSWORD" -> "Senha incorreta."
                    else -> "Credenciais inválidas. Por favor, tente novamente."
                }
            is FirebaseAuthInvalidUserException ->
                when (e.errorCode) {
                    "ERROR_USER_DISABLED" -> "Esta conta está desabilitada."
                    "ERROR_USER_NOT_FOUND" -> "Usuário inválido."
                    else -> "Esta conta não é válida. Por favor, tente novamente."
                }
            is FirebaseNetworkException -> "Sem conexão com a internet."

            else -> {
                val code = (e as? FirebaseAuthException)?.errorCode
                when(code){
                    "ERROR_EMAIL_ALREADY_IN_USE" -> "Esta conta já existe."
                    "ERROR_WEAK_PASSWORD" -> "A senha deve ter no mínimo 6 caracteres."
                    "ERROR_OPERATION_NOT_ALLOWED" -> "Login por e-mail/senha está desabilitado."
                    "ERROR_TOO_MANY_REQUESTS" -> "Muitas tentativas. Tente novamente mais tarde."
                    else -> e.localizedMessage?.substringBefore("\n") ?: "Falha na autenticação."
                }
            }

        }
    }
}