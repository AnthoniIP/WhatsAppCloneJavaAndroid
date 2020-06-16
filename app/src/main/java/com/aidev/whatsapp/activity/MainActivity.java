package com.aidev.whatsapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.aidev.whatsapp.R;
import com.aidev.whatsapp.fragments.ContatosFragment;
import com.aidev.whatsapp.fragments.ConversasFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private Toolbar toolbarPrincipal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarPrincipal = findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(toolbarPrincipal);

        //configurar abas
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)

                .add("Conversas", ConversasFragment.class)
                .add("Contatos", ContatosFragment.class)


                .create()
        );
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_sair:
                deslogarUsuario();
                break;

            case R.id.menu_configurações:
                abrirConfiguracoes();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    private void abrirConfiguracoes() {

        Intent intent = new Intent(this,ConfigActivity.class);
        startActivity(intent);

    }

    private void deslogarUsuario() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Sair");
        dialog.setMessage("Deseja realmente sair?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {

                    firebaseAuth.signOut();
                    finish();
                    Toast.makeText(getApplicationContext(), "Usuário desconectado, até logo", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        });

        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.create();
        dialog.show();


    }
}