package com.diarioproyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestablecerClave extends AppCompatActivity {

    private EditText email;

    private Button restablecer;

    private FirebaseAuth firebaseAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_clave);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Restablecer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        email = findViewById(R.id.email);
        restablecer = findViewById(R.id.boton_restablecer);
        firebaseAuth = FirebaseAuth.getInstance();
        
        restablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().isEmpty()){
                    resetPassword();
                }else{
                    Toast.makeText(RestablecerClave.this, "Rellene los campos requeridos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    }
    
    
    private void resetPassword(){

        firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RestablecerClave.this, "Se ha enviado un correo a este email para restablecer la contraseña", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(RestablecerClave.this, "No se pudo enviar el correo a este email", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    }
}
