package com.example.library_management_app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class booksFragment extends Fragment {
    Button btnaddbook,viewbooks;
    private MySQLiteOpenHelper helper;
    private BookDataSource bds;
    private View v;
    ListView list;
    private EditText search;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_books,container,false);
        btnaddbook = v.findViewById(R.id.btn_addbook);
        viewbooks = v.findViewById(R.id.btn_viewbooks);
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
        btnaddbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new NewBookFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        viewbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshListView("");
            }
        });

        registerForContextMenu(list);
        RefreshListView("");
        return v;
    }



    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ;
        if(item.getItemId() == R.id.delete){
            try {

                TextView txt_id = (TextView) info.targetView.findViewById(R.id.tvm_ID);
                int id = Integer.parseInt(txt_id.getText().toString());
                bds.deleteBookById(id);
                RefreshListView("");
            }
            catch (Exception e){
                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if(item.getItemId() == R.id.edit){
            try {
                TextView txt_id = (TextView) info.targetView.findViewById(R.id.tvm_ID);
                TextView category = (TextView) info.targetView.findViewById(R.id.tvm_category);
                TextView author = (TextView) info.targetView.findViewById(R.id.tvm_author);
                TextView bookname = (TextView) info.targetView.findViewById(R.id.tvm_bookname);
                TextView relase = (TextView) info.targetView.findViewById(R.id.tvm_release);
                TextView link = (TextView) info.targetView.findViewById(R.id.tvm_links);
                TextView about = (TextView) info.targetView.findViewById(R.id.tvm_aboutbook);


                int id = Integer.parseInt(txt_id.getText().toString());
                String Category = category.getText().toString();
                String Author = author.getText().toString();
                String BookName = bookname.getText().toString();
                String Relase = relase.getText().toString();
                String Link = link.getText().toString();
                String About = about.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putInt("ID", id);
                bundle.putString("Category",Category);
                bundle.putString("Author",Author);
                bundle.putString("BookName",BookName);
                bundle.putString("Relase",Relase);
                bundle.putString("Link",Link);
                bundle.putString("About",About);


                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                UpdateBookFragment updateBookFragment = new UpdateBookFragment();
                updateBookFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, updateBookFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            catch (Exception e){
                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        return  super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
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
