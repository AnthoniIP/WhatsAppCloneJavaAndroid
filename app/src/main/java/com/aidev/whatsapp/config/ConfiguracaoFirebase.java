package com.aidev.whatsapp.config;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*

created by Anthoni Ipiranga

*/   public class ConfiguracaoFirebase {

    private static DatabaseReference database;
    private static FirebaseAuth auth;

    //retorna a instancia do firebase
    public static DatabaseReference getFirebaseDatabase() {

        if (database == null) {

            database = FirebaseDatabase.getInstance().getReference();

        }

        return database;
    }

    public static com.google.firebase.auth.FirebaseAuth getFirebaseAutenticacao() {

        if(auth == null) {

            auth = com.google.firebase.auth.FirebaseAuth.getInstance();


        }

        return auth;

    }
}
