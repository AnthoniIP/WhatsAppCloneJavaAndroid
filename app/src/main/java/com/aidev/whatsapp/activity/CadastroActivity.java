package com.aidev.whatsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aidev.whatsapp.R;
import com.aidev.whatsapp.config.ConfiguracaoFirebase;
import com.aidev.whatsapp.helper.Base64Custom;
import com.aidev.whatsapp.helper.UsuarioFirebase;
import com.aidev.whatsapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private Button cadastrar;
    private FirebaseAuth autenticacao;
    private EditText nome, email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.EditNome);
        email = findViewById(R.id.EditEmail);
        senha = findViewById(R.id.EditSenha);


        cadastrar = findViewById(R.id.button2);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidarUsuario(nome.getText().toString(), email.getText().toString(), senha.getText().toString());

            }
        });


    }



    public void logar() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}