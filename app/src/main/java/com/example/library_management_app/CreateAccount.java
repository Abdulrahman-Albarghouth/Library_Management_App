package com.example.library_management_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {
    EditText fname,lname,phone,email,username,password,error;
    private Button btncreate;
    private MySQLiteOpenHelper helper;
    private UserDataSource uds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        fname = findViewById(R.id.etu_fname);
        lname = findViewById(R.id.etu_lname);
        phone = findViewById(R.id.etu_phone);
        email = findViewById(R.id.etu_email);
        username = findViewById(R.id.etu_username);
        password = findViewById(R.id.etu_password);
        btncreate = findViewById(R.id.btn_create);
        helper = new MySQLiteOpenHelper(this,"Library_DB",null,1);
        uds = new UserDataSource(helper);
    }

    public void createAccount(View view) {
        try {

            String Firstname = fname.getText().toString().trim();
            String Lastname = lname.getText().toString().trim();
            String Mobil = phone.getText().toString().trim();
            String Email = email.getText().toString().trim();
            String Username = username.getText().toString().trim();
            String Password = password.getText().toString().trim();



            if (Firstname.isEmpty() && Lastname.isEmpty() && Mobil.isEmpty() && Email.isEmpty() && Username.isEmpty() && Password.isEmpty()) {
                fname.setError(getString(R.string.Enter_First_Name));
                lname.setError(getString(R.string.Enter_Last_Name));
                phone.setError(getString(R.string.Enter_Phone_Number));
                email.setError(getString(R.string.Enter_Email));
                username.setError(getString(R.string.Enter_Username));
                password.setError(getString(R.string.Enter_User_Password));

                fname.requestFocus();
                return;
            }
            if (Firstname.isEmpty()) {
                fname.setError(getString(R.string.Enter_First_Name));
                fname.requestFocus();

                return;
            }
            if (Lastname.isEmpty()) {
                lname.setError(getString(R.string.Enter_Last_Name));
                lname.requestFocus();

                return;
            }
            if (Mobil.isEmpty()) {
                phone.setError(getString(R.string.Enter_Phone_Number));
                phone.requestFocus();

                return;
            }
            if (Email.isEmpty()) {
                email.setError(getString(R.string.Enter_Email));
                email.requestFocus();

                return;
            }
            if (!email.getText().toString().contains("@") || !email.getText().toString().endsWith(".com")) {
                email.setError(getString(R.string.invalid_email));
                email.requestFocus();
                return;
            }
            if (Username.isEmpty()) {
                username.setError(getString(R.string.Enter_Username));
                username.requestFocus();

                return;
            }
            if (Password.isEmpty()) {
                password.setError(getString(R.string.Enter_User_Password));
                password.requestFocus();

                return;
            }
            if (password.getText().toString().trim().length() < 8) {
                password.setError(getString(R.string.Password_Must_Be));
                password.requestFocus();
                return;
            }
            User u = new User(Firstname,Lastname,Long.parseLong(Mobil),Email,Username,Password,0);
            boolean result = uds.addNewUser(u);
            if (result) {
                onCreateDialog();
            } else {
                onCreateDialog1(getString(R.string.erro_message));
            }
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public Dialog onCreateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.Info);
        builder.setMessage(R.string.Account_Created_Successfully);
        builder.setIcon(R.drawable.ic_baseline_info_24);
        builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.create();
        return builder.show();
    }
    public Dialog onCreateDialog1(String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_baseline_info_24);
        builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        return builder.show();
    }
}