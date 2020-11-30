package com.example.pruebdb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Ingreso extends AppCompatActivity {

    private EditText et1;
    private EditText et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
        et1 = findViewById(R.id.edit_txt_ingreso_nombre);
        et2 = findViewById(R.id.edit_txt_ingreso_clave);
    }

    public void ingresar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Cuentas", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();


        String userName = et1.getText().toString();
        String clave = et2.getText().toString();
        if(!userName.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery
                    ("Select clave From usuario WHERE userName ='" + userName + "'", null);
            if(fila.moveToFirst()){

               if(!clave.equals(fila.getString(0).toString())){
                   Toast.makeText(this, "Clave incorrecta", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(this, "Ingreso Correctamente", Toast.LENGTH_LONG).show();
               }

            }else Toast.makeText(this, "Usuario no existe", Toast.LENGTH_SHORT).show();

        }else Toast.makeText(this, "Ingrese nombre de usuario", Toast.LENGTH_SHORT).show();

    }
}
