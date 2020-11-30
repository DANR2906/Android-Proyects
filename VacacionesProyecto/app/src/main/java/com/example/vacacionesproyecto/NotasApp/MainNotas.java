package com.example.vacacionesproyecto.NotasApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar ;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vacacionesproyecto.NotasApp.Adapter.NotasAdapter;
import com.example.vacacionesproyecto.NotasApp.DataBase.AdminSQLiteOpenHelper;
import com.example.vacacionesproyecto.NotasApp.Modelo.Nota;
import com.example.vacacionesproyecto.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainNotas extends AppCompatActivity {

    public static Activity auxActivity;


    public RecyclerView recyclerView;

    private NotasAdapter notasAdapter;
    private List<Nota> mNotas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notas);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        auxActivity = this;

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mNotas = new ArrayList<>();

        readNotas();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notas_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.buscar_menu:
                EditText busquedaEnt = findViewById(R.id.busqueda_ent);
                readNotas(busquedaEnt.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void readNotas(String param){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Notas", null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();

        Cursor cursor = baseDeDatos.query("notas", null , null, null, null, null, null);

        if(cursor.moveToFirst()){

            mNotas.clear();

            do{
                Nota nota = new Nota(cursor.getString(0), cursor.getString(2), cursor.getString(1));

                if(cursor.getString(0).toLowerCase().contains(param.toLowerCase())){
                    mNotas.add(nota);
                }

            }while(cursor.moveToNext());

            Collections.reverse(mNotas);

            notasAdapter = new NotasAdapter(this, mNotas);
            recyclerView.setAdapter(notasAdapter);

            baseDeDatos.close();

        }
    }
    public void readNotas(){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Notas", null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();

        Cursor cursor = baseDeDatos.query("notas", null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            mNotas.clear();
            do{
                Nota nota = new Nota(cursor.getString(0), cursor.getString(2), cursor.getString(1));

                mNotas.add(nota);


            }while(cursor.moveToNext());
        }
        Collections.reverse(mNotas);

        notasAdapter = new NotasAdapter(this, mNotas);
        recyclerView.setAdapter(notasAdapter);

        baseDeDatos.close();

    }

    public void nuevaNota(View view){
        Intent nuevaNota = new Intent(this, CrearNotas.class);
        startActivity(nuevaNota);
    }




}
