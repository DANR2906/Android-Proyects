package com.example.vacacionesproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vacacionesproyecto.NotasApp.MainNotas;

public class SelectorApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_app);
    }

    public void abrirNotas(View view){
        Intent notas = new Intent(this, MainNotas.class);
        startActivity(notas);
    }
}
