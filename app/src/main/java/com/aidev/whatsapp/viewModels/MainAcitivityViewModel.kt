package com.aidev.whatsapp.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aidev.whatsapp.activity.CadastroAcitivity
import com.aidev.whatsapp.config.ConfiguracaoFirebase
import com.aidev.whatsapp.helper.Base64Custom
import com.aidev.whatsapp.helper.UsuarioFirebase
import com.aidev.whatsapp.model.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    WhatsApp
 *  Date:       09/01/2021
 */

class MainActivityViewModel() :ViewModel(){


    private var autenticacao: FirebaseAuth? = null

    fun ValidarUsuario(nome: String, email: String, senha: String) {
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {

        } else {
            val usuario = Usuario()
            usuario.nome = nome
            usuario.email = email
            usuario.senha = senha
            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao()
            autenticacao!!.createUserWithEmailAndPassword(usuario.email, usuario.senha).addOnCompleteListener(this, OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                  
                    UsuarioFirebase.atualizarNomeUsuario(usuario.nome)
                    CadastroAcitivity.logar()
                    try {
                        val identificadorUsuario = Base64Custom.codificarBase64(usuario.email)
                        usuario.id = identificadorUsuario
                        usuario.salvar()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    var excecao = ""
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        excecao = "Digite uma senha mais forte"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        excecao = "Por favor digite um email válido"
                    } catch (e: FirebaseAuthUserCollisionException) {
                        excecao = "Está conta já existe"
                    } catch (e: Exception) {
                        excecao = "Erro ao cadastrar o usuário: " + e.message
                        e.printStackTrace()
                    }
                    Toast.makeText(mApplication, excecao, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(mApplication) as T
    }


}