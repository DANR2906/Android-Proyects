package com.diarioproyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.diarioproyect.Adapter.NotasAdapter;
import com.diarioproyect.Model.Nota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotasActivity extends AppCompatActivity {



    private String dia;

    private RecyclerView recyclerView;

    private NotasAdapter notasAdapter;
    private List<Nota> mNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);


        dia = getIntent().getStringExtra("Dia_Nota");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notas Día " + dia);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        readNotas();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.refresh:
                Intent intent = new Intent(this, NotasActivity.class);
                intent.putExtra("Dia_Nota", dia);
                finish();
                startActivity(intent);

                return true;

        }
        return false;
    }

    public void readNotas(){
        mNotas = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Notas", null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();

        Cursor cursor = baseDeDatos.query("notas", null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            mNotas.clear();
            do{
                Nota nota = new Nota(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

                if(nota.getDia().equals(dia))
                    mNotas.add(nota);


            }while(cursor.moveToNext());
        }
        Collections.reverse(mNotas);

        notasAdapter = new NotasAdapter(this, mNotas);
        recyclerView.setAdapter(notasAdapter);

        baseDeDatos.close();

    }

    public void nuevaNota(View view){
        Intent nuevaNota = new Intent(this, CrearNota.class);
        nuevaNota.putExtra("Dia_Nota", dia);
        this.finish();
        this.startActivity(nuevaNota);

    }
}
