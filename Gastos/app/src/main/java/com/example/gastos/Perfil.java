package com.example.gastos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {
    private TextView t1;
    private ImageButton ib1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().hide();

        t1 = findViewById(R.id.PERFIL_txt_bienvenida);
        ib1 = findViewById(R.id.PERFIL_IB_ImgPerfil);

        String nombre = getIntent().getStringExtra("Nombre");
        String apellido = getIntent().getStringExtra("Apellido");
        SharedPreferences preferencias = getSharedPreferences("DataName", Context.MODE_PRIVATE);
        String auxNombre = preferencias.getString("Nombre","");
        t1.setText(auxNombre);
        SharedPreferences preferencias_avatar = getSharedPreferences("avatar", Context.MODE_PRIVATE);
        String numAvatar = preferencias_avatar.getString("numAvatar", "");
        switch(numAvatar){
            case "1":
                ib1.setImageResource(R.mipmap.avatar1);
                break;
            case "2":
                ib1.setImageResource(R.mipmap.avatar2);
                break;
            case "3":
                ib1.setImageResource(R.mipmap.avatar3);
                break;
            case "4":
                ib1.setImageResource(R.mipmap.avatar4);
                break;
            case "5":
                ib1.setImageResource(R.mipmap.avatar5);
                break;
            case "6":
                ib1.setImageResource(R.mipmap.avatar6);
                break;
            case "7":
                ib1.setImageResource(R.mipmap.avatar7);
                break;
            case "8":
                ib1.setImageResource(R.mipmap.avatar8);
                break;
            case "9":
                ib1.setImageResource(R.mipmap.avatar9);
                break;
            case "10":
                ib1.setImageResource(R.mipmap.avatar10);
                break;

        }
    }

    public void ingreso(View view){
        Intent ingreso = new Intent(this, Ingreso.class);
        startActivity(ingreso);
    }
}
