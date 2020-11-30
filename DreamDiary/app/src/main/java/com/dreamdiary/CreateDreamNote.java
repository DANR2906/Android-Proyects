package com.dreamdiary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamdiary.Database.DataBaseController;
import com.dreamdiary.Database.IDataBaseController;

import java.util.ArrayList;
import java.util.Objects;

public class CreateDreamNote extends AppCompatActivity {
    EditText field_date;
    private EditText field_tittle;
    private EditText field_dream_text;
    private Button buttonSaveDream;

    IDataBaseController dataBaseController;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dream_note);

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

        buttonSaveDream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<EditText> fields = new ArrayList<>();
                fields.add(field_date);
                fields.add(field_tittle);
                fields.add(field_dream_text);

                if(!areFieldsEmpty(fields)) {
                    boolean insert = dataBaseController.insertDream(
                                                field_tittle.getText().toString(),
                                                field_date.getText().toString(),
                                                field_dream_text.getText().toString());
                    if (insert) {
                        Toast.makeText(CreateDreamNote.this,
                                getResources().getString(R.string.toast_createDreamNote_insertion_database_successful),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else
                    Toast.makeText(CreateDreamNote.this, getResources().getString(R.string.toast_createDreamNote_empty_field_error), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public boolean areFieldsEmpty(ArrayList<EditText> fields){
       for(EditText field : fields)
          if(field.getText().toString().isEmpty() || field.getText().toString().equals(""))
              return true;

       return false;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(field_date);
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private EditText field_date;
        public DatePickerFragment(EditText field_date){
            this.field_date = field_date;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            setDate(year, (month+1), day);
        }

        public void setDate(int year,int month,int day){
            String date_aux = day + "/" + month + "/" + year;
            field_date.setText(date_aux);
        }


    }

    public void finishActivity(View view){
        finish();
    }




}

