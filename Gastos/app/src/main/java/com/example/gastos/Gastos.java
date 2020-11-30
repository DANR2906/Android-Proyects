package com.example.gastos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Gastos extends AppCompatActivity {

    private LinearLayout ln1;
    private  LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT );
    private  LinearLayout.LayoutParams lpM = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT );

    private EditText et1;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        ln1 = findViewById(R.id.GASTOS_Ln_lista);
        et1 = findViewById(R.id.GASTOS_edtxt_titulo);

        leerBD();

        lpM.setMargins(50,50,0,0);
        //lpM.gravity = Gravity.CENTER;
        /**
         * for (int i=0; i<5; i++){
         *             Button button = new Button(this);
         *             //Asignamos propiedades de layout al boton
         *             button.setLayoutParams(lp);
         *             //Asignamos Texto al botón
         *             button.setText("Boton "+String.format("%02d", i ));
         *             //Añadimos el botón a la botonera
         *             ln1.addView(button);
         *         }
         */




    }


    public void leerBD(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BDGastos", null, 1);
         SQLiteDatabase database = admin.getReadableDatabase();


        Cursor fila = database.query
                ("gastos",null,null,null,null,null, null);

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
                    LinearLayout columnas = new LinearLayout(this);
                    columnas.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            300));
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(lp);
                    tv.setText("Titulo: " + titulo + "\nValor: $" + valor + "\nFecha: " + fecha);
                    tv.setTextSize(20);




                    boton = new Button(this);
                    boton.setLayoutParams(lpM);
                    boton.setText("Borrar: " + titulo);
                    boton.setBackgroundColor(Color.rgb(240,103,103));
                    boton.setOnClickListener(new ButtonsOnClickListener());





                    columnas.addView(tv);
                    columnas.addView(boton);

                    ln1.addView(columnas);
                }


            }while(fila.moveToNext());



                database.close();
        }else
        {
            database.close();
        }

    }

    public void borrarGasto(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BDGastos", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();
        SQLiteDatabase database2 = admin.getReadableDatabase();

        AdminSQLiteOpenHelper admin2 = new AdminSQLiteOpenHelper(this, "Contabilidad", null, 1);
        SQLiteDatabase databaseC = admin2.getWritableDatabase();
        SQLiteDatabase databaseC2 = admin2.getReadableDatabase();

        String titulo = et1.getText().toString();

        if(!titulo.isEmpty()){
            double valorGasto = 0;
            double totalGastos = 0;
            double totalAhorro = 0;
            Cursor fila = database2.rawQuery
                    ("SELECT valorGasto FROM gastos WHERE tituloGasto='" + titulo + "'", null);

            if(fila.moveToFirst()){
                    valorGasto = fila.getDouble(0);

            }else{
                Toast.makeText(this, "No se encontro el gasto", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor fila2 = databaseC2.rawQuery
                    ("SELECT totalGastado, totalAhorrado FROM contabilidad WHERE semana='" + "1" + "'", null);
            if(fila2.moveToFirst()){
                totalGastos = fila2.getDouble(0);
                totalAhorro = fila2.getDouble(1);

            }
            totalGastos -= valorGasto;
            totalAhorro += valorGasto;

            ContentValues upgrate = new ContentValues();
            upgrate.put("totalGastado", totalGastos);
            upgrate.put("totalAhorrado", totalAhorro);

            int cantidad2 = databaseC.update("contabilidad", upgrate, "semana='" + "1" +"'", null);
            databaseC.close();

            int cantidad = database.delete("gastos", "tituloGasto='" + titulo + "'", null);
            database.close();

            finish();
            startActivity(getIntent());
        }else {
            Toast.makeText(this, "Ingrese el titulo", Toast.LENGTH_SHORT).show();
            database.close();
            databaseC.close();
        }
    }

    public void borrarGastoBoton(String titulo){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BDGastos", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();
        SQLiteDatabase database2 = admin.getReadableDatabase();

        AdminSQLiteOpenHelper admin2 = new AdminSQLiteOpenHelper(this, "Contabilidad", null, 1);
        SQLiteDatabase databaseC = admin2.getWritableDatabase();
        SQLiteDatabase databaseC2 = admin2.getReadableDatabase();



        if(!titulo.isEmpty()){
            double valorGasto = 0;
            double totalGastos = 0;
            double totalAhorro = 0;
            Cursor fila = database2.rawQuery
                    ("SELECT valorGasto FROM gastos WHERE tituloGasto='" + titulo + "'", null);

            if(fila.moveToFirst()){
                valorGasto = fila.getDouble(0);

            }else{
                Toast.makeText(this, "No se encontro el gasto", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor fila2 = databaseC2.rawQuery
                    ("SELECT totalGastado, totalAhorrado FROM contabilidad WHERE semana='" + "1" + "'", null);
            if(fila2.moveToFirst()){
                totalGastos = fila2.getDouble(0);
                totalAhorro = fila2.getDouble(1);

            }
            totalGastos -= valorGasto;
            totalAhorro += valorGasto;

            ContentValues upgrate = new ContentValues();
            upgrate.put("totalGastado", totalGastos);
            upgrate.put("totalAhorrado", totalAhorro);

            int cantidad2 = databaseC.update("contabilidad", upgrate, "semana='" + "1" +"'", null);
            databaseC.close();

            int cantidad = database.delete("gastos", "tituloGasto='" + titulo + "'", null);
            database.close();

            finish();
            startActivity(getIntent());
        }else {
            Toast.makeText(this, "Ingrese el titulo", Toast.LENGTH_SHORT).show();
            database.close();
            databaseC.close();
        }
    }

    public void volver(View view){
        Intent ingreso = new Intent(this, Ingreso.class);
        startActivity(ingreso);
    }

    class ButtonsOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Button b = (Button) v;
            borrarGastoBoton(b.getText().toString().substring(8));
        }
    };

}
