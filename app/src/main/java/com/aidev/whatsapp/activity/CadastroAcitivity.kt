package com.aidev.whatsapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aidev.whatsapp.R
import com.aidev.whatsapp.viewModels.MainActivityViewModel

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    WhatsApp
 *  Date:       09/01/2021
 */

class CadastroAcitivity : AppCompatActivity() {

    private var viewModel: MainActivityViewModel by viewModels()

    private lateinit var cadastrar: Button
    private lateinit var nome: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_cadastro)

        nome = findViewById(R.id.EditNome)
        email = findViewById<EditText>(R.id.EditEmail)
        password = findViewById<EditText>(R.id.EditSenha)

        cadastrar.setOnClickListener {
            viewModel.ValidarUsuario(nome.text.toString(), email.getText().toString(), password.getText().toString())
        }


    }

    fun logar() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}