package com.dreamdiary.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dreamdiary.R;

public class DataBaseController implements IDataBaseController{

    private static AdminSQLiteOpenHelper databaseAdmin;
    private static SQLiteDatabase database;
    private Context context;

    public DataBaseController(Context context) {
        this.context = context;

        databaseAdmin = new AdminSQLiteOpenHelper(context, AdminSQLiteOpenHelper.DATABASE_NAME_DREAM_NOTES);

    }

    @Override
    public boolean insertDream(@NonNull final String TITLE, @NonNull final String DATE,
                               @NonNull final String TEXT){

        database = databaseAdmin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TITLE_", TITLE);
        values.put("DATE_", DATE);
        values.put("DREAM_TEXT_", TEXT);

        try {
            database.insert(AdminSQLiteOpenHelper.TABLE_NAME_DREAMS, null, values);
            Toast.makeText(context, context.getResources().getString(R.string.toast_createDreamNote_insertion_database_successful), Toast.LENGTH_SHORT).show();
            database.close();
            return true;
        } catch (Exception e) {
            Log.e("Insertion in DB error", e.getMessage());
            Toast.makeText(context, context.getResources().getString(R.string.toast_createDreamNote_insertion_database_error), Toast.LENGTH_SHORT).show();
            database.close();
            return false;
        }

    }

    @Override
    public boolean deleteDreamByID(@NonNull final String ID){

        database = databaseAdmin.getWritableDatabase();

        try {
            int delete = database.delete
                    (AdminSQLiteOpenHelper.TABLE_NAME_DREAMS,
                            "ID_='" + ID + "'",
                            null);
            database.close();
            return true;
        } catch (Exception e) {
            Log.e("Deleting in db Error", e.getMessage());
            Toast.makeText(context, context.getResources().getString(R.string.toast_main_deleting_database_error), Toast.LENGTH_SHORT).show();
            database.close();
            return false;
        }



    }


}
