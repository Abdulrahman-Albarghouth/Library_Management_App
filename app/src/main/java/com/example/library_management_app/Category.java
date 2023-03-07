package com.example.library_management_app;

import androidx.annotation.NonNull;

public class Category {
    private int id;
    private String CatName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }

    public Category(int id, String catName) {
        this.id = id;
        CatName = catName;
    }
    public Category( String catName) {

        CatName = catName;
    }



    @Override
    public String toString() {
        return CatName;
    }
}
