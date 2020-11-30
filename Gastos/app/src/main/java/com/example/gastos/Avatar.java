package com.example.gastos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Avatar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


    }

    public void irIngresoAvatar1(View view){
        SharedPreferences preferencias = getSharedPreferences("avatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("numAvatar", "1");
        editor.commit();
        Intent perfil = new Intent(this, Perfil.class);
        startActivity(perfil);
    }
    public void irIngresoAvatar2(View view){
        SharedPreferences preferencias = getSharedPreferences("avatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("numAvatar", "2");
        editor.commit();
        Intent perfil = new Intent(this, Perfil.class);
        startActivity(perfil);
    }
    public void irIngresoAvatar3(View view){
        SharedPreferences preferencias = getSharedPreferences("avatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("numAvatar", "3");
        editor.commit();
        Intent perfil = new Intent(this, Perfil.class);
        startActivity(perfil);
    }
    public void irIngresoAvatar4(View view){
        SharedPreferences preferencias = getSharedPreferences("avatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("numAvatar", "4");
        editor.commit();
        Intent perfil = new Intent(this, Perfil.class);
        startActivity(perfil);
    }
    public void irIngresoAvatar5(View view){
        SharedPreferences preferencias = getSharedPreferences("avatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("numAvatar", "5");
        editor.commit();
        Intent perfil = new Intent(this, Perfil.class);
        startActivity(perfil);
    }
    public void irIngresoAvatar6(View view){
        SharedPreferences preferencias = getSharedPreferences("avatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("numAvatar", "6");
        editor.commit();
        Intent perfil = new Intent(this, Perfil.class);
        startActivity(perfil);
    }
    public void irIngresoAvatar7(View view){
        SharedPreferences preferencias = getSharedPreferences("avatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("numAvatar", "7");
        editor.commit();
        Intent perfil = new Intent(this, Perfil.class);
        startActivity(perfil);
    }
    public void irIngresoAvatar8(View view){
        SharedPreferences preferencias = getSharedPreferences("avatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("numAvatar", "8");
        editor.commit();
        Intent perfil = new Intent(this, Perfil.class);
        startActivity(perfil);
    }
    public void irIngresoAvatar9(View view){
        SharedPreferences preferencias = getSharedPreferences("avatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("numAvatar", "9");
        editor.commit();
        Intent perfil = new Intent(this, Perfil.class);
        startActivity(perfil);
    }
    public void irIngresoAvatar10(View view){
        SharedPreferences preferencias = getSharedPreferences("avatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("numAvatar", "10");
        editor.commit();
        Intent perfil = new Intent(this, Perfil.class);
        startActivity(perfil);
    }
}
