package com.diarioproyect;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.HashMap;

public class Registro extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText clave;
    private Button registrar;



    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registro");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        username = findViewById(R.id.R_username);
        email = findViewById(R.id.R_email);
        clave = findViewById(R.id.R_clave);
        registrar = findViewById(R.id.R_registrar);
        
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);

                LayoutInflater inflater = getLayoutInflater();

                View view = inflater.inflate(R.layout.fragment_politicas, null);

                alerta.setView(view);

                final AlertDialog dialog = alerta.create();

                dialog.show();

                final Button aceptar;

                aceptar = view.findViewById(R.id.aceptar);


                aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txt_username = username.getText().toString();
                        String txt_email = email.getText().toString();
                        String txt_clave = clave.getText().toString();

                        if(txt_username.isEmpty() || txt_clave.isEmpty() || txt_email.isEmpty())
                            Toast.makeText(Registro.this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                        else if(txt_clave.length() < 7)
                            Toast.makeText(Registro.this, "La clave debe tener mas de 7 caracteres", Toast.LENGTH_SHORT).show();
                        else
                            registro(txt_username, txt_email, txt_clave);

                        dialog.cancel();
                    }
                });

            }
        });


        auth = FirebaseAuth.getInstance();



    }

    public void registro(final String username, String email, String clave){

        auth.createUserWithEmailAndPassword(email, clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userID = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put("id", userID);
                    hashMap.put("username", username);
                    hashMap.put("imageURL", "default");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(Registro.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                }else
                    Toast.makeText(Registro.this, "No se puede registrar con ese Email o Contraseña", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
