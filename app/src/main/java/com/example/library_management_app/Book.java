package com.example.library_management_app;

public class Book {

    int B_id;
    int Au_id;
    int C_id;
    String BookName;
    String ReleaseDate;
    String Link ;
    String About;

    public int getB_id() {
        return B_id;
    }

    public void setB_id(int b_id) {
        B_id = b_id;
    }

    public int getAu_id() {
        return Au_id;
    }

    public void setAu_id(int au_id) {
        Au_id = au_id;
    }

    public int getC_id() {
        return C_id;
    }

    public void setC_id(int c_id) {
        C_id = c_id;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public Book(int b_id, int au_id, int c_id, String bookName, String releaseDate, String link, String about) {
        B_id = b_id;
        Au_id = au_id;
        C_id = c_id;
        BookName = bookName;
        ReleaseDate = releaseDate;
        Link = link;
        About = about;
    }
    public Book(int au_id, int c_id, String bookName, String releaseDate, String link, String about) {
        Au_id = au_id;
        C_id = c_id;
        BookName = bookName;
        ReleaseDate = releaseDate;
        Link = link;
        About = about;
    }
}
