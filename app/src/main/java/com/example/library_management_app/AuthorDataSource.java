package com.example.library_management_app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AuthorDataSource {

    private MySQLiteOpenHelper helper;
    private SQLiteDatabase db;

    public AuthorDataSource(MySQLiteOpenHelper helper) {
        this.helper = helper;
        db = helper.getWritableDatabase();
    }

    public boolean addNewAuthor(Author u){

        ContentValues values = new ContentValues();
        values.put("FirstName",u.getFirstName());
        values.put("LastName",u.getLastName());
        values.put("BirthDate",u.getBirthDate());
        values.put("DeathDate",u.getDeathDate());
        values.put("About",u.getAbout());

        long id = db.insert("Author",null,values);
        if(id == -1){
            return false;
        }
        else return true;
    }

    public boolean updateAuthorData(Author u,int ID){
        ContentValues values = new ContentValues();
        values.put("FirstName",u.getFirstName());
        values.put("LastName",u.getLastName());
        values.put("BirthDate",u.getBirthDate());
        values.put("DeathDate",u.getDeathDate());
        values.put("About",u.getAbout());

        long id = db.update("Author",values,"Au_id = "+ID,null);
        if(id == -1 ){
            return false;
        }
        else return true;
    }

    public void deleteAuthorById(int id){

        db.delete("Author","Au_id=?",new String[]{Integer.toString(id)});
    }

    public List getAuthorsData(CharSequence s){
        ArrayList<Author> List = new ArrayList<>();
        Cursor cursor = db.query("Author",new String[]{"Au_id","FirstName","LastName","BirthDate","DeathDate","About"},null,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(0) ;
            String firstname = cursor.getString(1);
            String lastname = cursor.getString(2);
            String birthdate = cursor.getString(3);
            String deathdate = cursor.getString(4);
            String about = cursor.getString(5);
            Author a = new Author(firstname,lastname,birthdate,deathdate,about);
            a.setId(id);
            if(firstname.trim().toLowerCase().contains(s.toString().toLowerCase()) || lastname.trim().toLowerCase().contains(s.toString().toLowerCase())){
                List.add(a);
            }
            cursor.moveToNext();

        }
        cursor.close();
        return List;
    }

    public Author getAuthorById(int ID){
        Author a = null;
        Cursor cursor = db.query("Author",new String[]{"Au_id","FirstName","LastName","BirthDate","DeathDate","About"},"Au_id = "+ID,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(0) ;
            String firstname = cursor.getString(1);
            String lastname = cursor.getString(2);
            String birthdate = cursor.getString(3);
            String deathdate = cursor.getString(4);
            String about = cursor.getString(5);
            a = new Author(firstname,lastname,birthdate,deathdate,about);
            a.setId(id);
            cursor.moveToNext();

        }
        cursor.close();
        return a ;
    }
}
