package com.example.library_management_app;

public class Author {

    private int id;
    private String FirstName;
    private String LastName;
    private String BirthDate;
    private String DeathDate;
    private String About;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getDeathDate() {
        return DeathDate;
    }

    public void setDeathDate(String deathDate) {
        DeathDate = deathDate;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }


    public Author( String firstName, String lastName, String birthDate, String deathDate, String about) {

        FirstName = firstName;
        LastName = lastName;
        BirthDate = birthDate;
        DeathDate = deathDate;
        About = about;
    }

    public Author(int id, String firstName,String lastName) {
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
    }
    public Author() {
    }

    @Override
    public String toString() {
        return FirstName + " " + LastName;
    }
}
