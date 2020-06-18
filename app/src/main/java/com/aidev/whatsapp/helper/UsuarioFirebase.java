package com.aidev.whatsapp.helper;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.aidev.whatsapp.config.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UsuarioFirebase {

    public static String getIdentificadorUsuario() {

        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String email = usuario.getCurrentUser().getEmail();
        String identificadorUsuario = Base64Custom.codificarBase64(email);
        return identificadorUsuario;
    }

    public static FirebaseUser getUsuarioAtual() {

        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return usuario.getCurrentUser();


    }

    public static boolean atualizarFotoUsuario(Uri url) {

        try {

            FirebaseUser user = getUsuarioAtual();

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(url)
                    .build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("Perfil", "erro ao recuperar imagem de peril");
                    }
                }
            });

            return true;


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
}
