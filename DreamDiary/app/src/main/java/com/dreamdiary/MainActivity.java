package com.dreamdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.dreamdiary.Adapter.DreamAdapter;
import com.dreamdiary.Database.AdminSQLiteOpenHelper;
import com.dreamdiary.Model.Dream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DreamAdapter dreamAdapter;
    private List<Dream> dreamList;
    @SuppressLint("StaticFieldLeak")
    public static Activity myActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myActivity = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        recyclerView = findViewById(R.id.recycler_view_dreams);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dreamList = new ArrayList<>();

        findDreams(dreamList, dreamAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        findDreams(dreamList, dreamAdapter);
    }

    public void goToAddNewDream(View view){
        Intent intent = new Intent(MainActivity.this, CreateDreamNote.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //finish();
    }

    public void findDreams(List<Dream> myList, DreamAdapter myAdapter){

        AdminSQLiteOpenHelper databaseAdmin = new AdminSQLiteOpenHelper(this, AdminSQLiteOpenHelper.DATABASE_NAME_DREAM_NOTES);
        SQLiteDatabase database = databaseAdmin.getReadableDatabase();

        Cursor cursor = database.query
                (AdminSQLiteOpenHelper.TABLE_NAME_DREAMS,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            myList.clear();

            do{
                Dream dream = new Dream(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                myList.add(dream);
            }while(cursor.moveToNext());
        }
        Collections.reverse(myList);

        myAdapter = new DreamAdapter(this, myList);
        recyclerView.setAdapter(myAdapter);

        database.close();
        cursor.close();

    }


}
