package com.aidev.whatsapp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


public class Permissao {


    public static boolean validarPermissoes(String[] permisoesNecessarias, Activity activity, int requestCode) {


        if (Build.VERSION.SDK_INT >= 23) {

            List<String> permisoes = new ArrayList<>();


            //verifica as permisoes uma a uma e add na lista as permissoes nao fornecidas
            for (String permisao : permisoesNecessarias) {

                Boolean temPermisao = ContextCompat.checkSelfPermission(activity, permisao) == PackageManager.PERMISSION_GRANTED;

                if (!temPermisao) {

                    permisoes.add(permisao);

                }


            }

            if (permisoes.isEmpty()) return true;

            String[] novasPermissoes = new String[permisoes.size()];
            permisoes.toArray(novasPermissoes);

            //solicitar permissoes
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);


        }


        return true;
    }
}
