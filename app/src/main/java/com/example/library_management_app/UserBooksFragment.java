package com.example.library_management_app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserBooksFragment extends Fragment {
    private MySQLiteOpenHelper helper;
    private BookDataSource bds;
    private View v;
    ListView list;
    private EditText search;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_user_books,container,false);
        helper = new MySQLiteOpenHelper(getContext(),"Library_DB",null,1);
        bds = new BookDataSource(helper);
        list = (ListView) v.findViewById(R.id.booksListView);
        search = v.findViewById(R.id.et_searchbooks);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0 ){
                    RefreshListView("");
                }
                else {
                    RefreshListView(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        RefreshListView("");
        return v;
    }

    private void RefreshListView(CharSequence s) {
        try {
            BooksAdapter adapter = new BooksAdapter(getActivity(), R.layout.books_item_layout, bds.getBooksData(s));
            list.setAdapter(adapter);
        }
        catch (Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
