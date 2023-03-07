package com.example.library_management_app;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class NewUserFragment extends Fragment {
    EditText fname,lname,phone,email,username,password,error;
    private Button btnSave;
    RadioButton rbadmin,rbuser;
    RadioGroup rg;
    private int accesstype;
    private MySQLiteOpenHelper helper;
    private UserDataSource uds;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_new_user,container,false);
        fname = v.findViewById(R.id.etu_fname);
        lname = v.findViewById(R.id.etu_lname);
        phone = v.findViewById(R.id.etu_phone);
        email = v.findViewById(R.id.etu_email);
        username = v.findViewById(R.id.etu_username);
        password = v.findViewById(R.id.etu_password);
        btnSave = v.findViewById(R.id.btn_saveuser);
        rg = v.findViewById(R.id.rg_access);
        rbadmin = v.findViewById(R.id.rb_admin);
        rbuser = v.findViewById(R.id.rb_user);
        error = v.findViewById(R.id.et_error1);
        helper = new MySQLiteOpenHelper(getContext(),"Library_DB",null,1);
        uds = new UserDataSource(helper);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String Firstname = fname.getText().toString().trim();
                    String Lastname = lname.getText().toString().trim();
                    String Mobil = phone.getText().toString().trim();
                    String Email = email.getText().toString().trim();
                    String Username = username.getText().toString().trim();
                    String Password = password.getText().toString().trim();
                    getAccess();
                    int access = accesstype;


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
                    if (rbadmin.isChecked() == false&&  rbuser.isChecked() == false) {
                        error.setError(getString(R.string.Select_Access_Type));
                        error.requestFocus();

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
                    User u = new User(Firstname,Lastname,Long.parseLong(Mobil),Email,Username,Password,access);
                    boolean result = uds.addNewUser(u);
                    if (result) {
                        onCreateDialog();
                    } else {
                        onCreateDialog1(getString(R.string.erro_message));
                    }
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }

    public Dialog onCreateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.Info);
        builder.setMessage(R.string.Added_Successfully);
        builder.setIcon(R.drawable.ic_baseline_info_24);
        builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FragmentManager manager = getFragmentManager();
                assert manager != null;
                manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                manager.beginTransaction().commit();

            }
        });
        builder.create();
        return builder.show();
    }
    public Dialog onCreateDialog1(String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
    public int getAccess(){
        if(rbadmin.isChecked())
        {
            accesstype = 1;
        }
        else if(rbuser.isChecked())
        {
            accesstype = 0;
        }
        return accesstype;
    }
}

