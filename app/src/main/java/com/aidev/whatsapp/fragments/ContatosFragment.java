package com.aidev.whatsapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aidev.whatsapp.R;
import com.aidev.whatsapp.adapter.ContatosAdapter;
import com.aidev.whatsapp.config.ConfiguracaoFirebase;
import com.aidev.whatsapp.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ContatosFragment extends Fragment {

    private RecyclerView listaContatos;
    private ContatosAdapter adapter;
    private ArrayList<Usuario> contatosLista = new ArrayList<>();
    private DatabaseReference usuariosReference;
    private ValueEventListener valueEventListenerContatos;


    public ContatosFragment() {
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
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        //Configurações iniciais
        usuariosReference = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios");

        listaContatos = view.findViewById(R.id.recyclerListaContatos);

        //configurar adapter
        adapter = new ContatosAdapter(contatosLista, getActivity());


        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listaContatos.setLayoutManager(layoutManager);
        listaContatos.setHasFixedSize(true);
        listaContatos.setAdapter(adapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarContatos();
    }

    @Override
    public void onStop() {
        super.onStop();
        usuariosReference.removeEventListener(valueEventListenerContatos);
    }

    public void recuperarContatos() {
        valueEventListenerContatos = usuariosReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Usuario usuario = dados.getValue(Usuario.class);
                    contatosLista.add(usuario);
                }

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}