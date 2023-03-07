package com.example.library_management_app;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class NewAuthorFragment extends Fragment {
    EditText fname,lname,birth,death,about;
    private Button btnSave;
    private MySQLiteOpenHelper helper;
    private AuthorDataSource ads;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_new_author,container,false);
        fname = v.findViewById(R.id.et_fname);
        lname = v.findViewById(R.id.et_lname);
        birth = v.findViewById(R.id.et_bdate);
        death = v.findViewById(R.id.et_ddeath);
        about = v.findViewById(R.id.et_about);
        btnSave = v.findViewById(R.id.btn_saveauthor);
        helper = new MySQLiteOpenHelper(getContext(),"Library_DB",null,1);
        ads = new AuthorDataSource(helper);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String firstname = fname.getText().toString().trim();
                    String lastname = lname.getText().toString().trim();
                    String birthdate = birth.getText().toString().trim();
                    String deathdate = death.getText().toString().trim();
                    String abouthim = about.getText().toString().trim();

                    if (firstname.isEmpty() && lastname.isEmpty() && birthdate.isEmpty() && deathdate.isEmpty() && abouthim.isEmpty() ) {
                        fname.setError(getString(R.string.Enter_First_Name));
                        lname.setError(getString(R.string.Enter_Last_Name));
                        birth.setError(getString(R.string.Enter_Birth_Date));
                        death.setError(getString(R.string.Enter_Death_Date));
                        about.setError(getString(R.string.Enter_Details));

                        fname.requestFocus();
                        return;
                    }
                    if (firstname.isEmpty()) {
                        fname.setError(getString(R.string.Enter_First_Name));
                        fname.requestFocus();

                        return;
                    }
                    if (lastname.isEmpty()) {
                        lname.setError(getString(R.string.Enter_Last_Name));
                        lname.requestFocus();

                        return;
                    }
                    if (birthdate.isEmpty()) {
                        birth.setError(getString(R.string.Enter_Birth_Date));
                        birth.requestFocus();

                        return;
                    }
                    if (deathdate.isEmpty()) {
                        death.setError(getString(R.string.Enter_Death_Date));
                        death.requestFocus();

                        return;
                    }
                    if (abouthim.isEmpty()) {
                        about.setError(getString(R.string.Enter_Details));
                        about.requestFocus();

                        return;
                    }

                    Author au = new Author(firstname,lastname,birthdate,deathdate,abouthim);
                    boolean result = ads.addNewAuthor(au);
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
}
