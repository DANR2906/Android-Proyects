package com.diarioproyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Ingreso extends AppCompatActivity {

    private EditText email;
    private EditText clave;
    private Button bt_ingreso;
    private TextView restablecer;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ingreso");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.L_email);
        clave = findViewById(R.id.L_clave);
        bt_ingreso = findViewById(R.id.L_ingresar);
        bt_ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_clave = clave.getText().toString();

                if(txt_clave.isEmpty() || txt_email.isEmpty())
                    Toast.makeText(Ingreso.this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                else{
                    auth.signInWithEmailAndPassword(txt_email, txt_clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(Ingreso.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else
                                Toast.makeText(Ingreso.this, "Fallo de autentificación", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        restablecer = findViewById(R.id.restablecer_clave);

        restablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Ingreso.this, RestablecerClave.class);
                    startActivity(intent);
            }
        });



    }
}
