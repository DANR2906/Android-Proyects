package com.example.gastos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et1;
    private EditText et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        SharedPreferences preferencias = getSharedPreferences("DataName", Context.MODE_PRIVATE);
        String nombre = preferencias.getString("Nombre","");
        if(!nombre.isEmpty()){
            Intent perfil = new Intent(this, Perfil.class);
            startActivity(perfil);
        }

        et1 = findViewById(R.id.MAIN_edittxt_name);
        et2 = findViewById(R.id.MAIN_edittxt_apellido);


    }

    public void ingresar(View view){
        String nombre = et1.getText().toString();
        String apellido = et2.getText().toString();

        if(nombre.isEmpty() || apellido.isEmpty()){
            Toast.makeText(this, "Ingrese el campo", Toast.LENGTH_SHORT).show();
        }else{
            SharedPreferences preferencias = getSharedPreferences("DataName", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferencias.edit();
            edit.putString("Nombre", nombre);
            edit.putString("Apellido", apellido);
            edit.commit();

            Toast.makeText(this, "Perfil Creado", Toast.LENGTH_SHORT).show();
            Intent avatar = new Intent(this, Avatar.class);
            startActivity(avatar);
        }


    }
}
