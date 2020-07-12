package com.aidev.whatsapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aidev.whatsapp.R;
import com.aidev.whatsapp.model.Conversa;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversasAdapter extends RecyclerView.Adapter<ConversasAdapter.MyViewHolder> {

    private ArrayList<Conversa> conversaList;
    private Context context;

    public ConversasAdapter(ArrayList<Conversa> conversas, Context c) {
        this.conversaList = conversas;
        this.context = c;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_contatos, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Conversa conversa = conversaList.get(position);
        holder.nome.setText(conversa.getUsuarioExibicao().getNome());
        holder.ultimaConversa.setText(conversa.getUltimaMensagem());

        if (conversa.getUsuarioExibicao().getFoto() != null) {
            Uri url = Uri.parse(conversa.getUsuarioExibicao().getFoto());
            Glide.with(context).load(url).into(holder.foto);
        } else {
            holder.foto.setImageResource(R.drawable.padrao);
        }


    }

    @Override
    public int getItemCount() {
        return conversaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView foto;
        TextView nome, ultimaConversa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            foto = itemView.findViewById(R.id.imagemPerfil);
            nome = itemView.findViewById(R.id.textViewNome);
            ultimaConversa = itemView.findViewById(R.id.textViewEmail);

        }
    }
}
