package com.example.pruebdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText et1;
    private EditText et2;
    private EditText et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = findViewById(R.id.edit_txt_username);
        et2 = findViewById(R.id.edit_txt_clave);
        et3 = findViewById(R.id.edit_txt_name);
    }

    public void guardar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Cuentas", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String userName = et1.getText().toString();
        String password = et2.getText().toString();
        String name = et3.getText().toString();
        if(!userName.isEmpty() || !password.isEmpty() || !name.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("userName", userName);
            registro.put("clave", password);
            registro.put("nombre", name);

            BaseDeDatos.insert("usuario", null, registro);
            BaseDeDatos.close();

            et1.setText("");
            et2.setText("");
            et3.setText("");

        }
        Intent ingreso = new Intent(this, Ingreso.class);
        startActivity(ingreso);

    }

    public void buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Cuentas", null,1);
        SQLiteDatabase BaseDeDatosdatabase = admin.getReadableDatabase();



        String userName = et1.getText().toString();

        if(!userName.isEmpty()){
            Cursor fila = BaseDeDatosdatabase.rawQuery
                    ("SELECT clave, nombre FROM usuario WHERE userName ='" + userName + "'", null);

            if(fila.moveToFirst()){
                et2.setText(fila.getString(0));
                et3.setText(fila.getString(1));
                BaseDeDatosdatabase.close();
            }else {
                Toast.makeText(this, "No existe el usuario", Toast.LENGTH_SHORT).show();
                BaseDeDatosdatabase.close();
            }


        }else Toast.makeText(this, "Ingrese un usuario", Toast.LENGTH_SHORT).show();
    }

}
