package com.aidev.whatsapp.config;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/*

created by Anthoni Ipiranga

*/   public class ConfiguracaoFirebase {

    private static DatabaseReference database;
    private static FirebaseAuth auth;
    private static StorageReference storage;

    //retorna a instancia do firebase
    public static DatabaseReference getFirebaseDatabase() {

        if (database == null) {

            database = FirebaseDatabase.getInstance().getReference();

        }

        return database;
    }

    public static com.google.firebase.auth.FirebaseAuth getFirebaseAutenticacao() {

        if (auth == null) {

            auth = com.google.firebase.auth.FirebaseAuth.getInstance();


        }

        return auth;

    }

    public static StorageReference getFirebaseStorage() {

        if (storage == null) {

            storage = FirebaseStorage.getInstance().getReference();

        }

        return storage;

    }
}
