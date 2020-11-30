package com.example.gastosanuales;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQliteOpenHelper extends SQLiteOpenHelper {

    public AdminSQliteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE firstWeek(" +
                    "expenseTitle text PRIMARY KEY," +
                    "expenseValue double," +
                    "expenseDate text," +
                    "expenseNote text)");

            db.execSQL("CREATE TABLE secondWeek(" +
                    "expenseTitle text PRIMARY KEY," +
                    "expenseValue double," +
                    "expenseDate text," +
                    "expenseNote text)");

            db.execSQL("CREATE TABLE thirdWeek(" +
                    "expenseTitle text PRIMARY KEY," +
                    "expenseValue double," +
                    "expenseDate text," +
                    "expenseNote text)");

            db.execSQL("CREATE TABLE fourthWeek(" +
                    "expenseTitle text PRIMARY KEY," +
                    "expenseValue double," +
                    "expenseDate text," +
                    "expenseNote text)");

            db.execSQL("CREATE TABLE expenseSummary(" +
                    "month text PRIMARY KEY," +
                    "amountFirstWeek double DEFAULT 0," +
                    "amountSecondWeek double DEFAULT 0," +
                    "amountThirdWeek double DEFAULT 0," +
                    "amountFourthWeek double DEFAULT 0," +
                    "totalAmount double DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
