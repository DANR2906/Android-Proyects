package com.diarioproyect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.diarioproyect.Adapter.DiaAdapter;
import com.diarioproyect.Model.Dia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class DiarioActivity extends AppCompatActivity {

    private EditText textoDia;
    private List<Dia> mDias;
    private DiaAdapter diaAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Diario");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        textoDia = findViewById(R.id.entrada_dia);

        cargarDias();
    }

    public void guardarDia(View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Dias", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        if(existeDia())
            Toast.makeText(this, "Este dia ya fue creado anteriormente", Toast.LENGTH_SHORT).show();
        else
            agregarDia(baseDeDatos);

        baseDeDatos.close();

    }

    public boolean existeDia(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Dias", null, 1);
        SQLiteDatabase baseDeDatosR = admin.getReadableDatabase();

        Cursor cursor = baseDeDatosR.rawQuery("SELECT Fecha FROM dias WHERE Dia ='" + textoDia.getText().toString() + "'", null);

        if(cursor.moveToFirst()){
            baseDeDatosR.close();
            return true;
        }
        baseDeDatosR.close();
        return false;
    }

    public void agregarDia(SQLiteDatabase baseDeDatos){

        Calendar fecha = Calendar.getInstance();
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int mes = fecha.get(Calendar.MONTH);
        int año = fecha.get(Calendar.YEAR);

        if (!textoDia.getText().toString().isEmpty()) {

            ContentValues nota = new ContentValues();
            nota.put("Dia", textoDia.getText().toString());
            nota.put("Fecha", dia + "/" + mes + "/" + año);

            baseDeDatos.insert("dias", null, nota);

            textoDia.setText("");
            cargarDias();

        } else {
            Toast.makeText(this, "Rellene los campos", Toast.LENGTH_SHORT).show();
        }

        baseDeDatos.close();
    }

    public void cargarDias(){
        mDias = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Dias", null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();

        Cursor cursor = baseDeDatos.query("dias", null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            mDias.clear();
            do{
                Dia dia = new Dia(cursor.getString(0), cursor.getString(1));

                mDias.add(0, dia);


            }while(cursor.moveToNext());
        }

        diaAdapter = new DiaAdapter(this, mDias);
        recyclerView.setAdapter(diaAdapter);

        baseDeDatos.close();

    }



}
