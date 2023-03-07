package com.example.library_management_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {


    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE User( U_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " FirstName TEXT NOT NULL, LastName TEXT NOT NULL, " +
                "Phone INTEGER NOT NULL,"+"Email TEXT NOT NULL,"+"Username TEXT NOT NULL,"
                +"Password TEXT NOT NULL,"+ " AccessType INTEGER NOT NULL);";

        String query1 = " CREATE TABLE Category( C_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " CatName TEXT NOT NULL );";

        String query2 = " CREATE TABLE Author( Au_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " FirstName TEXT NOT NULL, LastName TEXT NOT NULL, " +
                "BirthDate TEXT NOT NULL,"+"DeathDate TEXT NOT NULL,"+"About TEXT NOT NULL);";

        String query3 = " CREATE TABLE Book( B_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " BookName TEXT NOT NULL, ReleaseDate TEXT NOT NULL, " +
                "Link TEXT NOT NULL,"+"About TEXT NOT NULL,"+"Au_id INTEGER NOT NULL,"
                +"C_id INTEGER NOT NULL);";


        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
