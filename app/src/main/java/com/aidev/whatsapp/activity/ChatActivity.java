package com.aidev.whatsapp.activity;

import android.net.Uri;
import android.os.Bundle;

import com.aidev.whatsapp.adapter.MensagensAdapter;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aidev.whatsapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private TextView textViewNome;
    private CircleImageView circleImageViewFoto;
    private EditText editMensagem;
    private FloatingActionButton sendMessage;
    private Usuario usuarioDestinatario;
    private String IDusuarioRemetente, IDusuarioDestinatario;
    private RecyclerView recyclerMensagens;
    private MensagensAdapter adapter;

    private DatabaseReference databaseReference;
    private DatabaseReference mensagensRef;
    private ChildEventListener childEventListenerMensagens;
    private List<Mensagem> listaDeMensagens = new ArrayList<>();


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
        textViewNome = findViewById(R.id.textViewNomeChat);
        circleImageViewFoto = findViewById(R.id.circleImagemPerfil);
        editMensagem = findViewById(R.id.editMessage);
        sendMessage = findViewById(R.id.fabSendMessage);
        recyclerMensagens = findViewById(R.id.reclyclerMensagens);

        //recuperar usuario remetente
        IDusuarioRemetente = UsuarioFirebase.getIdentificadorUsuario();

        //recuperar usuario destinatario
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            usuarioDestinatario = (Usuario) bundle.getSerializable("chatContato");
            textViewNome.setText(usuarioDestinatario.getNome());

            String foto = usuarioDestinatario.getFoto();
            if (foto != null) {

                Uri url = Uri.parse(usuarioDestinatario.getFoto());
                Glide.with(ChatActivity.this)
                        .load(url)
                        .into(circleImageViewFoto);


            } else {

                circleImageViewFoto.setImageResource(R.drawable.padrao);


            }

            IDusuarioDestinatario = Base64Custom.codificarBase64(usuarioDestinatario.getEmail());

        }

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarMensagem(view);
            }
        });

        //configurando adapter
        adapter = new MensagensAdapter(listaDeMensagens, getApplicationContext());

        //configurando recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerMensagens.setLayoutManager(layoutManager);
        recyclerMensagens.setHasFixedSize(true);
        recyclerMensagens.setAdapter(adapter);

        databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
        mensagensRef = databaseReference.child("mensagens")
                .child(IDusuarioRemetente)
                .child(IDusuarioDestinatario);


    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarMensagens();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mensagensRef.removeEventListener(childEventListenerMensagens);
    }

    public void enviarMensagem(View view) {

        String textoMensagem = editMensagem.getText().toString();

        if (!textoMensagem.isEmpty()) {

            Mensagem mensagem = new Mensagem();
            mensagem.setIdUsuario(IDusuarioRemetente);
            mensagem.setMensagem(textoMensagem);

            //salvar mensagem remetente
            salvarMensagem(IDusuarioRemetente, IDusuarioDestinatario, mensagem);

            //salvar mensagem para destinatario
            salvarMensagem(IDusuarioDestinatario,IDusuarioRemetente,mensagem);


        }

    }

    private void salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem) {

        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        mensagensRef = firebase.child("mensagens");

        mensagensRef.child(idRemetente)
                .child(idDestinatario)
                .push()
                .setValue(mensagem);

        editMensagem.setText("");

    }

    private void recuperarMensagens() {

        childEventListenerMensagens = mensagensRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Mensagem mensagem = dataSnapshot.getValue(Mensagem.class);
                listaDeMensagens.add(mensagem);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}