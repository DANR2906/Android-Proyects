package com.example.vacacionesproyecto.NotasApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.vacacionesproyecto.NotasApp.DataBase.AdminSQLiteOpenHelper;
import com.example.vacacionesproyecto.R;

import java.util.Calendar;

public class CrearNotas extends AppCompatActivity {


    private EditText tituloNota;
    private EditText textoNota;
    private RelativeLayout RLGeneral;

    private String colorTexto;
    private String colorFondo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_notas);

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

        tituloNota = findViewById(R.id.titulo_nota);
        textoNota = findViewById(R.id.texto_nota);
        RLGeneral = findViewById(R.id.relative_layout_general);

        tituloNota.setText(getIntent().getStringExtra("Titulo"));
        textoNota.setText(getIntent().getStringExtra("Texto"));
        cargarConfiguracion();


    }

    public void guardarNota(View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Notas", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        if(existeNota()){

            int aux = baseDeDatos.delete("notas","Titulo='" + tituloNota.getText().toString() + "'",null);
            agregarNota(baseDeDatos);


        }else {

            agregarNota(baseDeDatos);

        }
    }

    public boolean existeNota(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Notas", null, 1);
        SQLiteDatabase baseDeDatosR = admin.getReadableDatabase();

        Cursor cursor = baseDeDatosR.rawQuery("SELECT Fecha, Texto, ColorTexto FROM notas WHERE Titulo ='" + tituloNota.getText().toString() + "'", null);

        if(cursor.moveToFirst()){
            baseDeDatosR.close();
            return true;
        }
        baseDeDatosR.close();
        return false;
    }

    public void agregarNota(SQLiteDatabase baseDeDatos){

        Calendar fecha = Calendar.getInstance();
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int mes = fecha.get(Calendar.MONTH);
        int año = fecha.get(Calendar.YEAR);

        if (!tituloNota.getText().toString().isEmpty() && !textoNota.getText().toString().isEmpty()) {

            ContentValues nota = new ContentValues();
            nota.put("Titulo", tituloNota.getText().toString());
            nota.put("Fecha", dia + "/" + mes + "/" + año);
            nota.put("Texto", textoNota.getText().toString());
            nota.put("ColorTexto", colorTexto);
            nota.put("BackgroundColor", colorFondo);

            baseDeDatos.insert("notas", null, nota);

            tituloNota.setText("");
            textoNota.setText("");

            Intent mainNotas = new Intent(this, MainNotas.class);
            startActivity(mainNotas);
            finish();

        } else {
            Toast.makeText(this, getString(R.string.CrearNota_Alerta_CamposVacios), Toast.LENGTH_SHORT).show();
        }

        baseDeDatos.close();
    }

    public void cargarConfiguracion(){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Notas", null, 1);
            SQLiteDatabase database = admin.getReadableDatabase();

            Cursor cursor = database.rawQuery("SELECT ColorTexto, BackgroundColor FROM notas WHERE Titulo='" + tituloNota.getText().toString() + "'", null);



            if(cursor.moveToFirst()){

                colorTexto = cursor.getString(0);
                colorFondo = cursor.getString(1);

                switch (cursor.getString(0)){
                    case "WHITE":
                            tituloNota.setTextColor(Color.WHITE);
                            textoNota.setTextColor(Color.WHITE);
                        break;
                    case "BLACK":
                            tituloNota.setTextColor(Color.BLACK);
                            textoNota.setTextColor(Color.BLACK);
                        break;
                    case "RED":
                            tituloNota.setTextColor(Color.RED);
                            textoNota.setTextColor(Color.RED);
                        break;
                    case "GREEN":
                            tituloNota.setTextColor(Color.GREEN);
                            textoNota.setTextColor(Color.GREEN);
                        break;
                    case "GREEN2":
                        tituloNota.setTextColor(Color.rgb(100, 151, 77));
                        textoNota.setTextColor(Color.rgb(100, 151, 77));
                        break;
                    case "CYAN":
                        tituloNota.setTextColor(Color.CYAN);
                        textoNota.setTextColor(Color.CYAN);
                        break;
                    case"YELLOW":
                        tituloNota.setTextColor(Color.rgb(245, 255, 39));
                        textoNota.setTextColor(Color.rgb(245, 255, 39));
                        break;
                    case "PINK":
                        tituloNota.setTextColor(Color.rgb(255, 113, 168));
                        textoNota.setTextColor(Color.rgb(255, 113, 168));
                        break;

                    default:
                            tituloNota.setTextColor(Color.BLACK);
                            textoNota.setTextColor(Color.BLACK);

                }

                switch (cursor.getString(1)){
                    case "YELLOW":
                        RLGeneral.setBackgroundColor(Color.rgb(245, 255, 39));
                        break;
                    case "CYAN":
                        RLGeneral.setBackgroundColor(Color.CYAN);
                        break;
                    case "GREEN":
                        RLGeneral.setBackgroundColor(Color.GREEN);
                        break;

                    case "WHITE":
                        RLGeneral.setBackgroundColor(Color.WHITE);
                        break;

                    case "PINK":
                        RLGeneral.setBackgroundColor(Color.rgb(255, 113, 168));
                        break;

                    case "BLACK":
                        RLGeneral.setBackgroundColor(Color.BLACK);
                        break;

                    case "GREEN2":
                        RLGeneral.setBackgroundColor(Color.rgb(100, 151, 77));
                        break;

                    default:
                        RLGeneral.setBackgroundColor(Color.WHITE);

                }
            }else{

                colorTexto = "BLACK";
                colorFondo = "WHITE";

                RLGeneral.setBackgroundColor(Color.WHITE);

                tituloNota.setTextColor(Color.BLACK);
                textoNota.setTextColor(Color.BLACK);
            }

            database.close();

    }



    public void cerrarConfiguracion(View view){

        RelativeLayout configuracion = findViewById(R.id.configuracion_Colores);
        configuracion.setVisibility(View.INVISIBLE);

        configuracion = findViewById(R.id.configuracion_Colores_fondo);
        configuracion.setVisibility(View.INVISIBLE);
    }

    public void abrirConfiguracionColores(View view){
        cerrarConfiguracion(view);
        RelativeLayout configuracion = findViewById(R.id.configuracion_Colores);
        configuracion.setVisibility(View.VISIBLE);
    }

    public void abrirConfiguracionColoresFondo(View view){
        cerrarConfiguracion(view);
        RelativeLayout configuracion = findViewById(R.id.configuracion_Colores_fondo);
        configuracion.setVisibility(View.VISIBLE);
    }

    public void textColorBlanco(View view){
        colorTexto = "WHITE";
        tituloNota.setTextColor(Color.WHITE);
        textoNota.setTextColor(Color.WHITE);
    }

    public void textColorNegro(View view){
        colorTexto = "BLACK";
        tituloNota.setTextColor(Color.BLACK);
        textoNota.setTextColor(Color.BLACK);
    }

    public void textColorRojo(View view){
        colorTexto = "RED";
        tituloNota.setTextColor(Color.RED);
        textoNota.setTextColor(Color.RED);
    }

    public void textColorVerde(View view){
        colorTexto = "GREEN";
        tituloNota.setTextColor(Color.GREEN);
        textoNota.setTextColor(Color.GREEN);
    }

    public void textColorRosado(View view){
        colorTexto = "PINK";
        tituloNota.setTextColor(Color.rgb(255, 113, 168));
        textoNota.setTextColor(Color.rgb(255, 113, 168));
    }

    public void textColorAmarillo(View view){
        colorTexto = "YELLOW";
        tituloNota.setTextColor(Color.rgb(245, 255, 39));
        textoNota.setTextColor(Color.rgb(245, 255, 39));
    }

    public void textColorCyan(View view){
        colorTexto = "CYAN";
        tituloNota.setTextColor(Color.CYAN);
        textoNota.setTextColor(Color.CYAN);
    }

    public void textColorVerde2(View view){
        colorTexto = "GREEN2";
        tituloNota.setTextColor(Color.rgb(100, 151, 77));
        textoNota.setTextColor(Color.rgb(100, 151, 77));
    }

    public void backColorAmarillo(View view){
        colorFondo = "YELLOW";
        RLGeneral.setBackgroundColor(Color.rgb(245, 255, 39));
    }

    public void backColorVerde(View view){
        colorFondo = "GREEN";
        RLGeneral.setBackgroundColor(Color.GREEN);
    }

    public void backColorCyan(View view){
        colorFondo = "CYAN";
        RLGeneral.setBackgroundColor(Color.CYAN);
    }

    public void backColorBlanco(View view){
        colorFondo = "WHITE";
        RLGeneral.setBackgroundColor(Color.WHITE);
    }

    public void backColorRosado(View view){
        colorFondo = "PINK";
        RLGeneral.setBackgroundColor(Color.rgb(255, 113, 168));
    }

    public void backColornegro(View view){
        colorFondo = "BLACK";
        RLGeneral.setBackgroundColor(Color.BLACK);
    }

    public void backColorVerde2(View view){
        colorFondo = "GREEN2";
        RLGeneral.setBackgroundColor(Color.rgb(100, 151, 77));

    }

}
