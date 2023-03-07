package com.example.library_management_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnsingin, btncreateaccount,btnforgetpass;
    private EditText email,password;
    private MySQLiteOpenHelper helper;
    private UserDataSource uds;
    private Switch sw;
    private String My_Preferences= "com.example.library_management_app.preferences";
    private TextView tvpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new MySQLiteOpenHelper(this,"Library_DB",null,1);
        uds = new UserDataSource(helper);
        btnsingin = findViewById(R.id.btnsignin);
        btnforgetpass = findViewById(R.id.btnForgetpassword);
        btncreateaccount = findViewById(R.id.btnCreateAccount);
        email = findViewById(R.id.etemail);
        password = findViewById(R.id.etpassword);
        sw = findViewById(R.id.s_rememberme);

        SharedPreferences preferences = getSharedPreferences(My_Preferences,MODE_PRIVATE);
        String Email = preferences.getString("email","");
        String Password = preferences.getString("password","");
        boolean sw1 = preferences.getBoolean("switch",false);
        email.setText(Email);
        password.setText(Password);
        if(sw1){

            sw.setChecked(true);
        }

        btnsingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (email.getText().toString().trim().isEmpty() && password.getText().toString().trim().isEmpty()) {
                        email.setError(getString(R.string.Enter_email));
                        password.setError(getString(R.string.Enter_Password));
                        email.requestFocus();
                        return;
                    }
                    if (email.getText().toString().trim().isEmpty()) {
                        email.setError(getString(R.string.Enter_email));
                        email.requestFocus();

                        return;
                    }
                    if (!email.getText().toString().contains("@") || !email.getText().toString().endsWith(".com")) {
                        email.setError(getString(R.string.invalid_email));
                        email.requestFocus();
                        return;
                    }

                    if (password.getText().toString().isEmpty()) {
                        password.setError(getString(R.string.Enter_Password));
                        password.requestFocus();

                        return;
                    }
                    if (password.getText().toString().trim().length() < 8) {
                        password.setError(getString(R.string.Password_Must_Be));
                        password.requestFocus();
                        return;
                    }

                    boolean result = uds.Login(email.getText().toString().trim(), password.getText().toString().trim());

                    if (result ) {
                        controlRemembering();
                        User u = uds.getUserByEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim());
                        if(u.getAccessType() == 1){
                            Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                            intent.putExtra("ID",u.getId());
                            startActivity(intent);
                        }
                        else {

                            Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                            intent.putExtra("ID",u.getId());
                            startActivity(intent);
                        }

                    }
                    else {
                        onCreateDialog(getString(R.string.error),getString(R.string.Check_Email_Password),getString(R.string.Ok));
                    }


                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }





            }
        });
        btncreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });
        btnforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    void controlRemembering(){
        if (sw.isChecked()) {
            SharedPreferences.Editor editor = getSharedPreferences(My_Preferences, MODE_PRIVATE).edit();
            editor.putString("email", email.getText().toString().trim());
            editor.putString("password", password.getText().toString().trim());
            editor.putBoolean("switch", true);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = getSharedPreferences(My_Preferences, MODE_PRIVATE).edit();
            editor.remove("email");
            editor.remove("password");
            editor.remove("switch");
            editor.commit();
        }
    }
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.question);
        builder.setMessage(R.string.Exit);
        builder.setIcon(R.drawable.ic_baseline_help_24);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();

    }

    public Dialog onCreateDialog(String error, String Message, String btn) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(error);
        builder.setMessage(Message);
        builder.setIcon(R.drawable.ic_baseline_error_24);
        builder.setPositiveButton(btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                email.setText("");
                password.setText("");
            }
        });
        builder.create();
        return builder.show();
    }
}