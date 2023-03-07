package com.example.library_management_app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDataSource {

    private MySQLiteOpenHelper helper;
    private SQLiteDatabase db;

    public UserDataSource(MySQLiteOpenHelper helper) {
        this.helper = helper;
        db = helper.getWritableDatabase();
    }
    public boolean Login(String email,String password){
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE Email = ? AND Password = ?",new String[]{email,password});
        if(cursor.getCount() > 0){
            return true;
        }
        else return false;
    }

    public boolean addNewUser(User u){

        ContentValues values = new ContentValues();
        values.put("FirstName",u.getFirstName());
        values.put("LastName",u.getLastName());
        values.put("Phone",u.getPhoneNumber());
        values.put("Email",u.getEmail());
        values.put("Username",u.getUserName());
        values.put("Password",u.getPassword());
        values.put("AccessType",u.getAccessType());

        long id = db.insert("User",null,values);
        if(id == -1){
            return false;
        }
        else return true;
    }

    public boolean updateUserData(User u,int ID){
        ContentValues values = new ContentValues();

        values.put("FirstName",u.getFirstName());
        values.put("LastName",u.getLastName());
        values.put("Phone",u.getPhoneNumber());
        values.put("Email",u.getEmail());
        values.put("Username",u.getUserName());
        values.put("Password",u.getPassword());
        values.put("AccessType",u.getAccessType());

        long id = db.update("User",values,"U_id = "+ID,null);
        if(id == -1 ){
            return false;
        }
        else return true;
    }

    public void deleteUserById(int id){

        db.delete("User","U_id=?",new String[]{Integer.toString(id)});
    }

    public List getUsersData(CharSequence s){
        ArrayList<User> List = new ArrayList<>();
        Cursor cursor = db.query("User",new String[]{"U_id","FirstName","LastName","Phone","Email","Username","Password","AccessType"},null,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(0) ;
            String firstname = cursor.getString(1);
            String lastname = cursor.getString(2);
            long phone = cursor.getLong(3);
            String email = cursor.getString(4);
            String username = cursor.getString(5);
            String password = cursor.getString(6);
            int accesstype = cursor.getInt(7);
            User u = new User(firstname,lastname,phone,email,username,password,accesstype);
            u.setId(id);
            if(firstname.trim().toLowerCase().contains(s.toString().toLowerCase()) || lastname.trim().toLowerCase().contains(s.toString().toLowerCase())){
                List.add(u);
            }
            cursor.moveToNext();

        }
        cursor.close();
        return List;
    }


    public User getUserById(int ID){
        Cursor cursor = db.query("User",new String[]{"U_id","FirstName","LastName","Phone","Email","Username","Password","AccessType"},"U_id = ? ",new String[]{ID+""},null,null,null,null);
        cursor.moveToFirst();
        User u = null;
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0) ;
            String firstname = cursor.getString(1);
            String lastname = cursor.getString(2);
            long phone = cursor.getLong(3);
            String email = cursor.getString(4);
            String username = cursor.getString(5);
            String password = cursor.getString(6);
            int accesstype = cursor.getInt(7);
            u = new User(firstname,lastname,phone,email,username,password,accesstype);
            u.setId(id);
            cursor.moveToNext();
        }
        cursor.close();
        return u;
    }

    public User getUserByEmailAndPassword(String Email,String Password){
        Cursor cursor = db.query("User",new String[]{"U_id","FirstName","LastName","Phone","Email","Username","Password","AccessType"},"Email = ? AND Password = ?",new String[]{Email,Password},null,null,null,null);
        cursor.moveToFirst();
        User u = null;
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0) ;
            String firstname = cursor.getString(1);
            String lastname = cursor.getString(2);
            long phone = cursor.getLong(3);
            String email = cursor.getString(4);
            String username = cursor.getString(5);
            String password = cursor.getString(6);
            int accesstype = cursor.getInt(7);
            u = new User(firstname,lastname,phone,email,username,password,accesstype);
            u.setId(id);
            cursor.moveToNext();
        }
        cursor.close();
        return u;
    }



}
