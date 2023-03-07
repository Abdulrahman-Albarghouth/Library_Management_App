package com.example.library_management_app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookDataSource {


    private MySQLiteOpenHelper helper;
    private SQLiteDatabase db;

    public BookDataSource(MySQLiteOpenHelper helper) {
        this.helper = helper;
        db = helper.getWritableDatabase();
    }


    public boolean addNewBook(Book b){

        ContentValues values = new ContentValues();
        values.put("BookName",b.getBookName());
        values.put("ReleaseDate",b.getReleaseDate());
        values.put("Link",b.getLink());
        values.put("About",b.getAbout());
        values.put("Au_id",b.getAu_id());
        values.put("C_id",b.getC_id());


        long id = db.insert("Book",null,values);
        if(id == -1){
            return false;
        }
        else return true;
    }

    public boolean updateBookData(Book b,int ID){
        ContentValues values = new ContentValues();
        values.put("BookName",b.getBookName());
        values.put("ReleaseDate",b.getReleaseDate());
        values.put("Link",b.getLink());
        values.put("About",b.getAbout());
        values.put("Au_id",b.getAu_id());
        values.put("C_id",b.getC_id());

        long id = db.update("Book",values,"B_id = "+ID,null);
        if(id == -1 ){
            return false;
        }
        else return true;
    }

    public void deleteBookById(int id){

        db.delete("Book","B_id=?",new String[]{Integer.toString(id)});
    }

    public List getBooksData(CharSequence s){
        ArrayList<Book> List = new ArrayList<>();
        Cursor cursor = db.query("Book",new String[]{"B_id","BookName","ReleaseDate","Link","About","Au_id","C_id"},null,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(0) ;
            String BookName = cursor.getString(1);
            String RelaseDate = cursor.getString(2);
            String Link = cursor.getString(3);
            String About = cursor.getString(4);
            int au_id = cursor.getInt(5);
            int c_id = cursor.getInt(6);
            Book b = new Book(id,au_id,c_id,BookName,RelaseDate,Link,About);
            if(BookName.trim().toLowerCase().contains(s.toString().toLowerCase()) || About.trim().toLowerCase().contains(s.toString().toLowerCase())){
                List.add(b);
            }
            cursor.moveToNext();

        }
        cursor.close();
        return List;
    }

}
