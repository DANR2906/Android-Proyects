package com.example.gastos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE gastos(" +
                    "tituloGasto text PRIMARY KEY, " +
                    "valorGasto double," +
                    "fechaGasto text)");
            db.execSQL("CREATE TABLE contabilidad(" +
                    "semana text PRIMARY KEY, " +
                    "totalGastado double, " +
                    "totalAhorrado double, " +
                    "presupuesto double)");
             db.execSQL("CREATE TABLE gastosPasados(" +
                    "tituloGasto text PRIMARY KEY, " +
                    "valorGasto double," +
                    "fechaGasto text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
