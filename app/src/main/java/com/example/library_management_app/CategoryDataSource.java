package com.example.library_management_app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CategoryDataSource {

    private MySQLiteOpenHelper helper;
    private SQLiteDatabase db;

    public CategoryDataSource(MySQLiteOpenHelper helper) {
        this.helper = helper;
        db = helper.getWritableDatabase();
    }

    public boolean addNewCategory(Category c){

        ContentValues values = new ContentValues();
        values.put("CatName",c.getCatName());

        long id = db.insert("Category",null,values);
        if(id == -1){
            return false;
        }
        else return true;
    }

    public boolean updateCategoryData(Category c,int ID){
        ContentValues values = new ContentValues();
        values.put("CatName",c.getCatName());

        long id = db.update("Category",values,"C_id = "+ID,null);
        if(id == -1 ){
            return false;
        }
        else return true;
    }

    public void deleteCategoryById(int id){

        db.delete("Category","C_id=?",new String[]{Integer.toString(id)});
    }

    public List getCategoryData(CharSequence s){
        ArrayList<Category> List = new ArrayList<>();
        Cursor cursor = db.query("Category",new String[]{"C_id","CatName"},null,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(0) ;
            String name = cursor.getString(1);
            Category c = new Category(id,name);
            if(name.trim().toLowerCase().contains(s.toString().toLowerCase())){
                List.add(c);
            }
            cursor.moveToNext();

        }
        cursor.close();
        return List;
    }

    public Category getCategoryById(int ID){
        Category c = null;
        Cursor cursor = db.query("Category",new String[]{"C_id","CatName"},"C_id = "+ID,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(0) ;
            String name = cursor.getString(1);
            c = new Category(id,name);
            cursor.moveToNext();

        }
        cursor.close();
        return c;
    }

}
