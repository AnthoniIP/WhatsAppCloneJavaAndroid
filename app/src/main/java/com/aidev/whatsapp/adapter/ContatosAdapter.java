package com.aidev.whatsapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aidev.whatsapp.R;
import com.aidev.whatsapp.model.Usuario;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.MyViewHolder> {

    private ArrayList<Usuario> listaContatos;
    private Context context;


    public ContatosAdapter(ArrayList<Usuario> contatosLista, Context c) {

        this.listaContatos = contatosLista;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Usuario usuario = listaContatos.get(position);
        holder.nome.setText(usuario.getNome());
        holder.email.setText(usuario.getEmail());

        if (usuario.getFoto() != null) {

            Uri uri = Uri.parse(usuario.getFoto());

            Glide.with(context).load(uri).into(holder.foto);

        } else {
            holder.foto.setImageResource(R.drawable.padrao);
        }


    }

    @Override
    public int getItemCount() {
        return listaContatos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView foto;
        TextView nome, email;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.imagemPerfil);
            nome = itemView.findViewById(R.id.textViewNome);
            email = itemView.findViewById(R.id.textViewEmail);
        }
    }

}
