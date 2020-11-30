package com.diarioproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void  startIngreso(View view){

        Intent intent = new Intent(this, Ingreso.class);
        startActivity(intent);

    }

    public void startRegistro(View view){

        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);

    }

    public void startDiario(View view){
        Intent intent = new Intent(this, DiarioActivity.class);
        startActivity(intent);
    }
}
