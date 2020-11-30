package com.dreamdiary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamdiary.Database.AdminSQLiteOpenHelper;
import com.dreamdiary.Database.DataBaseController;
import com.dreamdiary.Database.IDataBaseController;
import com.dreamdiary.Model.Dream;

import java.util.ArrayList;
import java.util.Objects;

public class EditDreamNote extends AppCompatActivity {

    EditText field_date;
    private EditText field_tittle;
    private EditText field_dream_text;
    private Button buttonSaveDream;
    private static String DreamID;

    IDataBaseController dataBaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dream_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dataBaseController = new DataBaseController(this);

        field_date = findViewById(R.id.field_date);
        field_tittle = findViewById(R.id.field_tittle);
        field_dream_text = findViewById(R.id.field_dream_text);
        buttonSaveDream = findViewById(R.id.button_save_dream);

        DreamID = getIntent().getStringExtra("DreamId_");

        fillFields();

        buttonSaveDream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<EditText> fields = new ArrayList<>();
                fields.add(field_date);
                fields.add(field_tittle);
                fields.add(field_dream_text);

                if(!areFieldsEmpty(fields)) {
                    dataBaseController.deleteDreamByID(DreamID);

                    boolean insert = dataBaseController.insertDream(
                            field_tittle.getText().toString(),
                            field_date.getText().toString(),
                            field_dream_text.getText().toString());
                    if (insert) {
                        Toast.makeText(EditDreamNote.this, getResources().getString(R.string.toast_createDreamNote_insertion_database_successful), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else
                    Toast.makeText(EditDreamNote.this, getResources().getString(R.string.toast_createDreamNote_empty_field_error), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void fillFields(){
        field_date.setText(getIntent().getStringExtra("Date_"));
        field_tittle.setText(getIntent().getStringExtra("Title_"));
        field_dream_text.setText(getIntent().getStringExtra("DreamText_"));

    }

    public boolean areFieldsEmpty(ArrayList<EditText> fields){
        for(EditText field : fields)
            if(field.getText().toString().isEmpty() || field.getText().toString().equals(""))
                return true;

        return false;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new CreateDreamNote.DatePickerFragment(field_date);
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public void finishActivity(View view){
        finish();
    }

}