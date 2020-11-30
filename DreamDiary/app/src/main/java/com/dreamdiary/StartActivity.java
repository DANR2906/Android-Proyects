package com.dreamdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser;

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void startLogin(View view){
        Toast.makeText(this, getResources().getString(R.string.toast_login_error), Toast.LENGTH_SHORT).show();

        /*
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        */
    }



    public void startSignin(View view){
        Toast.makeText(this, getResources().getString(R.string.toast_signin_error), Toast.LENGTH_SHORT).show();
        /*
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
        */
    }

    public void startMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}