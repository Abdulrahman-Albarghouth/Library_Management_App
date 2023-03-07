package com.example.library_management_app;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import java.util.List;

public class NewBookFragment extends Fragment {
    private EditText bookname,relasedate,link,about;
    private Spinner category , author;
    private MySQLiteOpenHelper helper;
    private CategoryDataSource cds;
    private AuthorDataSource ads;
    private BookDataSource bds;
    private Category c;
    private Author a;
    private List list ,list1;
    private Button btnSave;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_new_books,container,false);
        helper = new MySQLiteOpenHelper(getContext(),"Library_DB",null,1);
        cds = new CategoryDataSource(helper);
        ads = new AuthorDataSource(helper);
        bds = new BookDataSource(helper);
        list = cds.getCategoryData("");
        list1 = ads.getAuthorsData("");
        category = v.findViewById(R.id.sp_category);
        author = v.findViewById(R.id.sp_author);
        btnSave = v.findViewById(R.id.btn_savebook);
        bookname = v.findViewById(R.id.etb_bookname);
        relasedate = v.findViewById(R.id.etb_relasasedate);
        link = v.findViewById(R.id.etb_link);
        about = v.findViewById(R.id.etb_about);
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(getContext(), android.R.layout.simple_spinner_dropdown_item,list);
        category.setAdapter(categoryArrayAdapter);

        ArrayAdapter<Author> authorArrayAdapter = new ArrayAdapter<Author>(getContext(), android.R.layout.simple_spinner_dropdown_item,list1);
        author.setAdapter(authorArrayAdapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                c = (Category) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        author.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                a = (Author) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String BookName = bookname.getText().toString().trim();
                    String Release = relasedate.getText().toString().trim();
                    String Link = link.getText().toString().trim();
                    String About = about.getText().toString().trim();

                    if (BookName.isEmpty() && Release.isEmpty() && Link.isEmpty() && About.isEmpty()) {
                        bookname.setError(getString(R.string.Enter_Book_Name));
                        relasedate.setError(getString(R.string.Enter_Release_Date));
                        link.setError(getString(R.string.Enter_Link));
                        about.setError(getString(R.string.Enter_Details_About_Book));

                        category.requestFocus();
                        return;
                    }
                    if (BookName.isEmpty()) {
                        bookname.setError(getString(R.string.Enter_Book_Name));
                        bookname.requestFocus();

                        return;
                    }
                    if (Release.isEmpty()) {
                        relasedate.setError(getString(R.string.Enter_Release_Date));
                        relasedate.requestFocus();

                        return;
                    }
                    if (Link.isEmpty()) {
                        link.setError(getString(R.string.Enter_Link));
                        link.requestFocus();

                        return;
                    }
                    if (About.isEmpty()) {
                        about.setError(getString(R.string.Enter_Details_About_Book));
                        about.requestFocus();

                        return;
                    }
                    Book b = new Book(a.getId(),c.getId(),BookName,Release,Link,About);
                    boolean result = bds.addNewBook(b);
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
