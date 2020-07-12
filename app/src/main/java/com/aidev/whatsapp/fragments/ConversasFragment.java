package com.aidev.whatsapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aidev.whatsapp.R;
import com.aidev.whatsapp.activity.ChatActivity;
import com.aidev.whatsapp.adapter.ConversasAdapter;
import com.aidev.whatsapp.config.ConfiguracaoFirebase;
import com.aidev.whatsapp.helper.RecyclerItemClickListener;
import com.aidev.whatsapp.helper.UsuarioFirebase;
import com.aidev.whatsapp.model.Conversa;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class ConversasFragment extends Fragment {

    private RecyclerView recyclerConversas;
    private ConversasAdapter adapter;
    private ArrayList<Conversa> conversas = new ArrayList<>();
    private DatabaseReference databaseReference;
    private DatabaseReference conversasRef;
    private ChildEventListener childEventListenerConversas;


    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        recyclerConversas = view.findViewById(R.id.recyclerConversas);

        //configurar adapter
        adapter = new ConversasAdapter(conversas, getActivity());

        //configurar recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerConversas.setLayoutManager(layoutManager);
        recyclerConversas.setHasFixedSize(true);
        recyclerConversas.setAdapter(adapter);

        //configurar evento de clique
        recyclerConversas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(), recyclerConversas, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Conversa conversa = conversas.get(position);

                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra("chatContato", conversa.getUsuarioExibicao());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
                )
        );

        //configurar referencia da conversa no firebase
        String idUsuario = UsuarioFirebase.getIdentificadorUsuario();
        databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
        conversasRef = databaseReference.child("conversas").child(idUsuario);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarConversas();
    }

    @Override
    public void onStop() {
        super.onStop();
        conversasRef.removeEventListener(childEventListenerConversas);
    }

    public void recuperarConversas() {

        childEventListenerConversas = conversasRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Conversa conversa = dataSnapshot.getValue(Conversa.class);
                conversas.add(conversa);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}