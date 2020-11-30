package com.dreamdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText field_email;
    private EditText field_password;
    private Button button_continue_login;
    private ProgressBar progressbar_login;
    
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        field_email = findViewById(R.id.field_email);
        field_password = findViewById(R.id.field_password);
        button_continue_login = findViewById(R.id.button_continue_login);
        progressbar_login = findViewById(R.id.progressbar_login);
        
        firebaseAuth = FirebaseAuth.getInstance();
        
        button_continue_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar_login.setVisibility(View.VISIBLE);
                String str_email = field_email.getText().toString();
                String str_password = field_password.getText().toString();
                
                if(str_email.isEmpty() || str_password.isEmpty())
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.toast_login_empty_field_error), Toast.LENGTH_SHORT).show();
                else{
                    firebaseAuth.signInWithEmailAndPassword(str_email, str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.toast_login_authentication_error), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressbar_login.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }

        });

    }
}