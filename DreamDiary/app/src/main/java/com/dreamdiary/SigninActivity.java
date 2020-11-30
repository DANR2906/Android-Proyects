package com.dreamdiary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.Timer;
import java.util.HashMap;

public class SigninActivity extends AppCompatActivity {

    private EditText field_username;
    private EditText field_email;
    private EditText field_password;
    private EditText field_birthdate;
    private Button button_continue_sign_in;
    private ProgressBar progressbar_sign_in;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        field_username = findViewById(R.id.field_username);
        field_email = findViewById(R.id.field_email);
        field_password = findViewById(R.id.field_password);
        field_birthdate = findViewById(R.id.field_birthdate);

        button_continue_sign_in = findViewById(R.id.button_continue_sign_in);
        progressbar_sign_in = findViewById(R.id.progressbar_sign_in);

        button_continue_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_username = field_username.getText().toString();
                String str_email = field_email.getText().toString();
                String str_password = field_password.getText().toString();
                String str_birthdate = field_birthdate.getText().toString();

                if(str_username.isEmpty() || str_email.isEmpty() || str_password.isEmpty() || str_birthdate.isEmpty())
                    Toast.makeText(SigninActivity.this, getResources().getString(R.string.toast_sign_in_empty_field_error), Toast.LENGTH_SHORT).show();
                else if(str_password.length() < 5)
                    Toast.makeText(SigninActivity.this, getResources().getString(R.string.toast_sign_in_password_minimum_length), Toast.LENGTH_SHORT).show();
                else
                    register(str_username, str_email, str_password, str_birthdate);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void register(final String username, String email, String password, final String birthdate){
        progressbar_sign_in.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userID = firebaseUser.getUid();

                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put("id", userID);
                    hashMap.put("username", username);
                    hashMap.put("birthdate", birthdate);

                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });

                }else
                    Toast.makeText(SigninActivity.this, getResources().getString(R.string.toast_sign_in_register_error), Toast.LENGTH_SHORT).show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressbar_sign_in.setVisibility(View.INVISIBLE);
            }
        }, 1000);

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new CreateDreamNote.DatePickerFragment(field_birthdate);
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }


}