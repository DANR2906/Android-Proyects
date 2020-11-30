package com.diarioproyect;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.diarioproyect.Fragments.PostFragment;
import com.diarioproyect.Model.Nota;
import com.diarioproyect.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class CrearNota extends AppCompatActivity {


    private EditText tituloNota;
    private EditText textoNota;
    private RelativeLayout RLGeneral;

    private String dia;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nota);

        dia = getIntent().getStringExtra("Dia_Nota");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Crear nota Dia " + dia);
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



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crear_nota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.compartir:

                compartirNota();

                return true;

        }
        return false;
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

        Cursor cursor = baseDeDatosR.rawQuery("SELECT Fecha, Texto, Dia FROM notas WHERE Titulo ='" + tituloNota.getText().toString() + "'", null);

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
            nota.put("Texto", textoNota.getText().toString());
            nota.put("Fecha", dia + "/" + mes + "/" + año);
            nota.put("Dia", this.dia);


            baseDeDatos.insert("notas", null, nota);

            tituloNota.setText("");
            textoNota.setText("");

            Intent mainNotas = new Intent(this, NotasActivity.class);
            mainNotas.putExtra("Dia_Nota", this.dia);
            this.finish();
            this.startActivity(mainNotas);


        } else {
            Toast.makeText(this,"Todos los espacios son requeridos", Toast.LENGTH_SHORT).show();
        }

        baseDeDatos.close();
    }

    private void compartirNota(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(CrearNota.this);

            LayoutInflater inflater = getLayoutInflater();

            View view = inflater.inflate(R.layout.fragment_post, null);

            builder.setView(view);

            AlertDialog dialog = builder.create();

            dialog.show();


            final ImageView img_perfil;
            final TextView username;

            final TextView msg_contenido;
            final TextView estado_animo;

            ImageButton cara_triste;
            ImageButton cara_normal;
            ImageButton cara_feliz;

            Button btn_postear;


            DatabaseReference reference;

            img_perfil = view.findViewById(R.id.imagen_perfil);
            username = view.findViewById(R.id.username);
            msg_contenido = view.findViewById(R.id.contenido_texto);
            estado_animo = view.findViewById(R.id.estado_animo);
            estado_animo.setText("Normal");
            btn_postear = view.findViewById(R.id.P_postear);



            final String userid = firebaseUser.getUid();

            String mensajePost = "Dia " + dia + "\n" + tituloNota.getText().toString() + "\n" + textoNota.getText().toString();

            msg_contenido.setText(mensajePost);
            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    username.setText(user.getUsername());
                    if(user.getImageURL().equals("default"))
                        img_perfil.setImageResource(R.drawable.ic_person_red);
                    else
                        Glide.with(CrearNota.this).load(user.getImageURL()).into(img_perfil);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            btn_postear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mensaje = msg_contenido.getText().toString();
                    String animo = estado_animo.getText().toString();

                    if(!mensaje.isEmpty()){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id", reference.push().getKey());
                        hashMap.put("sender", firebaseUser.getUid());
                        hashMap.put("mensaje", mensaje);
                        hashMap.put("animo", animo);

                        reference.child("Posts").push().setValue(hashMap);
                        Toast.makeText(CrearNota.this, "Nota compartida con exito", Toast.LENGTH_SHORT).show();

                    }


                    else
                        Toast.makeText(CrearNota.this, "Rellene los campos", Toast.LENGTH_SHORT).show();

                    msg_contenido.setText("");
                }
            });

            cara_triste = view.findViewById(R.id.cara_triste);
            cara_normal = view.findViewById(R.id.cara_normal);
            cara_feliz = view.findViewById(R.id.cara_feliz);

            cara_triste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    estado_animo.setText("Triste");
                }
            });

            cara_normal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    estado_animo.setText("Normal");
                }
            });

            cara_feliz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    estado_animo.setText("Feliz");
                }
            });
        } else
        {
            android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(this);
            alerta.setMessage("Debe iniciar sesión para compartir la nota, si acepta esta se perdera, se recomienda guardarla primero");
            alerta.setTitle("Advertencia");

            alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   Intent intent = new Intent(CrearNota.this, StartActivity.class);
                   finish();
                   startActivity(intent);
                }
            });

            alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            android.app.AlertDialog dialog = alerta.create();

            dialog.show();
        }





    }







}
