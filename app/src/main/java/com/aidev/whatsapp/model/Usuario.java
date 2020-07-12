package com.aidev.whatsapp.model;

import com.aidev.whatsapp.config.ConfiguracaoFirebase;
import com.aidev.whatsapp.helper.UsuarioFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*

created by Anthoni Ipiranga

*/   public class Usuario implements Serializable {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String foto;

    public Usuario() {
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Exclude
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void salvar() {

        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference usuario = databaseReference.child("usuarios").child(getId());

        usuario.setValue(this);

    }

    public void atualizar() {

        String identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();
        DatabaseReference fire = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference usuariosRef = fire.child("usuarios").child(identificadorUsuario);

        usuariosRef.updateChildren(converterParaMap());

    }

    @Exclude
    public Map<String, Object> converterParaMap() {

        HashMap<String, Object> usuarioMap = new HashMap<>();

        usuarioMap.put("nome", getNome());
        usuarioMap.put("email", getEmail());
        usuarioMap.put("foto", getFoto());

        return usuarioMap;
    }
}
