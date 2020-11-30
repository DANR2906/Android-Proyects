package com.example.gastos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Gastos2 extends AppCompatActivity {

    private LinearLayout ln1;
    private  LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT );
    private  LinearLayout.LayoutParams lpM = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos2);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        ln1 = findViewById(R.id.GASTOS_Ln_listaAnterior);


        leerBD();

        lpM.setMargins(100,25,0,0);

    }


    public void leerBD(){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BDGastosCopy", null, 1);
        SQLiteDatabase database = admin.getReadableDatabase();


        Cursor fila = database.query
                ("gastosPasados",null,null,null,null,null, null);

        if(fila.moveToFirst()){

            fila.moveToFirst();

            do {
                String titulo = fila.getString(fila.getColumnIndex("tituloGasto"));
                String valor = fila.getString(fila.getColumnIndex("valorGasto"));
                String fecha = fila.getString(fila.getColumnIndex("fechaGasto"));




                if("actualizacion Gastos".equals(titulo)){

                    continue;
                }else
                {
                    if("NUEVA SEMANA".equals(titulo)){

                        continue;
                    }else {


                        LinearLayout columnas = new LinearLayout(this);

                        columnas.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));

                        TextView tv = new TextView(this);
                        tv.setLayoutParams(lp);
                        tv.setText("Titulo: " + titulo + "\nValor: $" + valor + "\nFecha: " + fecha);
                        tv.setTextSize(20);

                        TextView separador = new TextView(this);
                        separador.setLayoutParams(lp);
                        separador.setTextSize(20);
                        separador.setText("\t\t\t/ / / / /");

                        ln1.addView(separador);

                        columnas.addView(tv);

                        ln1.addView(columnas);
                    }
                }


            }while(fila.moveToNext());

            database.close();
        }else
        {
            database.close();
        }

    }


    public void volver(View view){
        Intent ingreso = new Intent(this, Ingreso.class);
        startActivity(ingreso);
    }

}
