package com.aidev.whatsapp.activity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aidev.whatsapp.R;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbarPrincipal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarPrincipal = findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(toolbarPrincipal);


    }
}