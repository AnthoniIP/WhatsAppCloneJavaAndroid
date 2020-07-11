package com.aidev.whatsapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

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


    public void ValidarUsuario(String nome, String email, String senha) {

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {

            Toast.makeText(getApplicationContext(), "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();

        } else {

            final Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);


            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(getApplicationContext(), "Sucesso ao cadastrar usuário.", Toast.LENGTH_SHORT).show();

                                UsuarioFirebase.atualizarNomeUsuario(usuario.getNome());
                                logar();

                                try {

                                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                                    usuario.setId(identificadorUsuario);
                                    usuario.salvar();


                                } catch (Exception e) {

                                    e.printStackTrace();

                                }

                            } else {

                                String excecao = "";
                                try {
                                    throw task.getException();

                                } catch (FirebaseAuthWeakPasswordException e) {
                                    excecao = "Digite uma senha mais forte";

                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    excecao = "Por favor digite um email válido";
                                } catch (FirebaseAuthUserCollisionException e) {
                                    excecao = "Está conta já existe";
                                } catch (Exception e) {

                                    excecao = "Erro ao cadastrar o usuário: " + e.getMessage();
                                    e.printStackTrace();

                                }

                                Toast.makeText(getApplicationContext(), excecao, Toast.LENGTH_SHORT).show();

                            }


                        }
                    });


        }


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