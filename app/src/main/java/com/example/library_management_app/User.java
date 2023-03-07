package com.example.library_management_app;

public class User {


    private int id;
    private String FirstName;
    private String LastName;
    private long PhoneNumber;
    private String Email;
    private String UserName;
    private String Password;
    private int AccessType;


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public long getPhoneNumber() {
        return PhoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    public int getAccessType() {
        return AccessType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setPhoneNumber(long phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setAccessType(int accessType) {
        AccessType = accessType;
    }

    public User(String firstName, String lastName, long phoneNumber, String email, String userName, String password, int accessType) {
        FirstName = firstName;
        LastName = lastName;
        PhoneNumber = phoneNumber;
        Email = email;
        UserName = userName;
        Password = password;
        AccessType = accessType;
    }
}
