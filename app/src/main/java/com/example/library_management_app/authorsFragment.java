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

public class authorsFragment extends Fragment {
    Button btnaddauthor,viewauthors;
    private MySQLiteOpenHelper helper;
    private AuthorDataSource ads;
    private View v;
    ListView list;
    private EditText search;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_authors,container,false);

        btnaddauthor = v.findViewById(R.id.btn_addauthor);
        viewauthors = v.findViewById(R.id.btn_viewauthors);
        helper = new MySQLiteOpenHelper(getContext(),"Library_DB",null,1);
        ads = new AuthorDataSource(helper);
        list = (ListView) v.findViewById(R.id.authorsListView);
        search = v.findViewById(R.id.et_searchauthor);

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
        btnaddauthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new NewAuthorFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        viewauthors.setOnClickListener(new View.OnClickListener() {
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
                ads.deleteAuthorById(id);
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
                TextView firstname = (TextView) info.targetView.findViewById(R.id.tvm_firstname);
                TextView lastname = (TextView) info.targetView.findViewById(R.id.tvm_lastname);
                TextView birthdate = (TextView) info.targetView.findViewById(R.id.tvm_birth);
                TextView deathdate = (TextView) info.targetView.findViewById(R.id.tvm_death);
                TextView about = (TextView) info.targetView.findViewById(R.id.tvm_about);


                int id = Integer.parseInt(txt_id.getText().toString());
                String fname = firstname.getText().toString();
                String lname = lastname.getText().toString();
                String birth = birthdate.getText().toString();
                String death = deathdate.getText().toString();
                String abouthim = about.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putInt("ID", id);
                bundle.putString("fname",fname);
                bundle.putString("lname",lname);
                bundle.putString("birth",birth);
                bundle.putString("death",death);
                bundle.putString("about",abouthim);


                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                UpdateAuthorFragment updateAuthorFragment = new UpdateAuthorFragment();
                updateAuthorFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, updateAuthorFragment);
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
            AuthorAdapter adapter = new AuthorAdapter(getActivity(), R.layout.authors_item_layout, ads.getAuthorsData(s));
            list.setAdapter(adapter);
        }
        catch (Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
