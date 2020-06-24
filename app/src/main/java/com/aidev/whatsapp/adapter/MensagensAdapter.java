package com.aidev.whatsapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aidev.whatsapp.R;
import com.aidev.whatsapp.helper.UsuarioFirebase;
import com.aidev.whatsapp.model.Mensagem;
import com.aidev.whatsapp.model.Usuario;
import com.bumptech.glide.Glide;

import java.util.List;

public class MensagensAdapter extends RecyclerView.Adapter<MensagensAdapter.MyViewHolder> {

    private static final int TIPO_REMETENTE = 0;
    private static final int TIPO_DESTINATARIO = 1;


    private Context context;
    List<Mensagem> mensagemList;

    public MensagensAdapter(List<Mensagem> mensagemList, Context c) {

        this.mensagemList = mensagemList;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = null;

        if (viewType == TIPO_REMETENTE) {

            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mensagem_remetente, parent, false);

        } else if (viewType == TIPO_DESTINATARIO) {

            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mensagem_remetente, parent, false);

        }

        return new MyViewHolder(item);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Mensagem mensage = mensagemList.get(position);
        String msg = mensage.getMensagem();
        String imagem = mensage.getImagem();

        if (imagem != null) {

            Uri url = Uri.parse(imagem);
            Glide.with(context).load(url).into(holder.imagem);
            holder.mensagem.setVisibility(View.GONE);


        } else {

            holder.mensagem.setText(msg);
            holder.imagem.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        return mensagemList.size();
    }

    @Override
    public int getItemViewType(int position) {

        Mensagem mensage = mensagemList.get(position);
        String idUsuario = UsuarioFirebase.getIdentificadorUsuario();

        if (idUsuario.equals(mensage.getIdUsuario())) {

            return TIPO_REMETENTE;

        }
        return TIPO_DESTINATARIO;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mensagem;
        ImageView imagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mensagem = itemView.findViewById(R.id.mensagemTexto);
            imagem = itemView.findViewById(R.id.imageMensagemFoto);
        }
    }

}
