package com.aidev.whatsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aidev.whatsapp.R;
import com.aidev.whatsapp.config.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email, senha;
    private Button logar;
    private TextView textViewCadastro;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        autenticarUsuario();
        abrirTelaCadastro();


    }

    public void abrirTelaCadastro() {

        textViewCadastro = findViewById(R.id.textViewCadastro);

        textViewCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });


    }

    public void autenticarUsuario() {


        logar = findViewById(R.id.button);
        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = findViewById(R.id.textInputEditText2);
                senha = findViewById(R.id.textInputEditText);

                String emailDigitado = email.getText().toString();
                String senhaDigitada = senha.getText().toString();

                if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Por favor preencha todos os campos.", Toast.LENGTH_SHORT).show();

                } else {

                    autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                    autenticacao.signInWithEmailAndPassword(emailDigitado, senhaDigitada).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {

                                try {

                                } catch (Exception e) {

                                    Toast.makeText(getApplicationContext(), "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        }
                    });


                }


            }
        });


    }
}