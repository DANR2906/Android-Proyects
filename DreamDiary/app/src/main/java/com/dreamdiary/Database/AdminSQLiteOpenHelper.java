package com.dreamdiary.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME_DREAM_NOTES = "DreamNotes.db";
    public static final String TABLE_NAME_DREAMS = "dreams";

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name) {
        super(context, name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE dreams(" +
                "ID_ integer PRIMARY KEY AUTOINCREMENT," +
                "TITLE_ text," +
                "DATE_ date DEFAULT '00/00/2020'," +
                "DREAM_TEXT_ text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
