package com.aidev.whatsapp.activity;

import android.net.Uri;
import android.os.Bundle;

import com.aidev.whatsapp.config.ConfiguracaoFirebase;
import com.aidev.whatsapp.helper.Base64Custom;
import com.aidev.whatsapp.helper.UsuarioFirebase;
import com.aidev.whatsapp.model.Mensagem;
import com.aidev.whatsapp.model.Usuario;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aidev.whatsapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private TextView nome;
    private CircleImageView fotoCircleView;
    private EditText message;
    private FloatingActionButton sendMessage;
    private Usuario usuarioDestinatario;
    private String IDusuarioRemetente, IDusuarioDestinatario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //configurações toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //configuraçoes iniciais
        nome = findViewById(R.id.textViewNomeChat);
        fotoCircleView = findViewById(R.id.circleImagemPerfil);
        message = findViewById(R.id.editMessage);
        sendMessage = findViewById(R.id.fabSendMessage);

        //recuperar usuario remetente
        IDusuarioRemetente = UsuarioFirebase.getIdentificadorUsuario();

        //recuperar usuario destinatario
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            usuarioDestinatario = (Usuario) bundle.getSerializable("chatContato");
            nome.setText(usuarioDestinatario.getNome());

            String foto = usuarioDestinatario.getFoto();
            if (foto != null) {

                Uri url = Uri.parse(usuarioDestinatario.getFoto());
                Glide.with(ChatActivity.this)
                        .load(url)
                        .into(fotoCircleView);


            } else {

                fotoCircleView.setImageResource(R.drawable.padrao);


            }

            IDusuarioDestinatario = Base64Custom.codificarBase64(usuarioDestinatario.getEmail());

        }

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarMensagem(view);
            }
        });


    }

    public void enviarMensagem(View view) {

        String textoMensagem = message.getText().toString();

        if (!textoMensagem.isEmpty()) {

            Mensagem mensagem = new Mensagem();
            mensagem.setMensagem(textoMensagem);
            mensagem.setIdUsuario(IDusuarioRemetente);


            //salvar mensagem remetente
            salvarMensagem(IDusuarioRemetente, IDusuarioDestinatario, mensagem);


        }

    }

    private void salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem) {

        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference mensagemRef = firebase.child("mensagens");

        mensagemRef.child(idRemetente)
                .child(idDestinatario)
                .push()
                .setValue(mensagem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                message.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getParent(), "Falha ao enviar mensagem, por favor tente novamente", Toast.LENGTH_SHORT).show();
            }
        });

    }
}