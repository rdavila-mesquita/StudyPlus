package com.example.study.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class AppViewModel: ViewModel() {
    val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val _assuntos = MutableStateFlow<List<Assunto>>(emptyList())
    val assuntos: StateFlow<List<Assunto>> = _assuntos.asStateFlow()
    private val _assuntoEspecifico = MutableStateFlow<Assunto?>(null)
    val assuntoEspecifico: StateFlow<Assunto?> = _assuntoEspecifico.asStateFlow()
    val assuntosNConcluidos: StateFlow<List<Assunto>> = assuntos.map { listaCompleta ->
        listaCompleta.filter { !it.concluida }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),emptyList())
    val assuntosConcluidos: StateFlow<List<Assunto>> = assuntos.map { listaCompleta ->
        listaCompleta.filter { it.concluida }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),emptyList())

    fun saveAssunto(titulo: String, assunto: String, userId: String?){
        if (userId.isNullOrBlank()){
            Log.i("###", "Usuário não autenticado")
        }
        val refDoc = db.collection("users")
            .document(userId.toString())
            .collection("assuntos")
            .document()
        val assunto = Assunto(refDoc.id, titulo, assunto)

        refDoc.set(assunto).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Log.i("###","Meta salva com sucesso!")
            } else {
                Log.i("###","erro", task.exception)
            }
        }
    }

    fun getAssuntos(){
        val userId = auth.currentUser?.uid
        if (userId == null){
            Log.i("###", "Usuário não autenticado")
            _assuntos.value = emptyList()
            return
        }

        db.collection("users")
            .document(userId)
            .collection("assuntos")
            .addSnapshotListener { snapshot, error ->
                if (error != null){

                    Log.i("###", "Erro ao buscar assuntos", error)
                    return@addSnapshotListener
                }

                if (snapshot != null){
                    val assuntosList = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Assunto::class.java)
                    }
                    _assuntos.value = assuntosList
                    Log.i("###", "Assuntos: $assuntosList")
                }
            }
    }

    fun getAssuntoEspecifico(userId: String?, assuntoId: String) {
        if (userId.isNullOrBlank()) {
            Log.i("###", "Usuário não autenticado")
            _assuntoEspecifico.value = null
            return
        }

        db.collection("users")
            .document(userId)
            .collection("assuntos")
            .document(assuntoId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.i("###", "Erro ao buscar assunto", error)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    _assuntoEspecifico.value = snapshot.toObject(Assunto::class.java)
                }else{
                    Log.w("###", "Meta não encontrada")
                    _assuntoEspecifico.value = null
                }
            }
    }

        fun limparAssuntoEspecifico(){
            _assuntoEspecifico.value = null
        }


        fun updateAssunto(novoTitulo: String, novoAssunto: String, assuntoId: String) {
            val userId = auth.currentUser?.uid
            if (userId.isNullOrBlank()) {
                Log.i("###", "Usuário não autenticado")
                return
            }

            val refDoc = db.collection("users")
                .document(userId)
                .collection("assuntos")
                .document(assuntoId)

            val atualizacao = mapOf(
                "titulo" to novoTitulo,
                "assunto" to novoAssunto
            )

            refDoc.update(atualizacao).addOnSuccessListener {
                Log.i("###", "Meta atualizada com sucesso!")
            }
                .addOnFailureListener { error ->
                    Log.i("###", "Erro ao atualizar meta", error)
                }
        }

        fun deleteAssunto(userId: String?, assuntoId: String) {
            if (userId.isNullOrBlank()) {
                Log.i("###", "Usuário não autenticado")
                return
            }
            val refDoc = db.collection("users")
                .document(userId)
                .collection("assuntos")
                .document(assuntoId)

            refDoc.delete().addOnSuccessListener {
                Log.i("###", "Assunto deletado com sucesso!")
            }
                .addOnFailureListener { error ->
                    Log.i("###", "Erro ao deletar assunto", error)
                }
        }

    fun atualizaStatus(assuntoId: String, novoStatus: Boolean){
        val userId = auth.currentUser?.uid
        if (userId.isNullOrBlank()){
            Log.i("###", "Usuário não autenticado")
            return
        }

        val refDoc = db.collection("users")
            .document(userId)
            .collection("assuntos")
            .document(assuntoId)

        refDoc.update("concluida", novoStatus).addOnSuccessListener {
            Log.i("###", "Status atualizado com sucesso!")
        }
            .addOnFailureListener { error ->
                Log.i("###", "Erro ao atualizar status", error)
            }
    }


    }

