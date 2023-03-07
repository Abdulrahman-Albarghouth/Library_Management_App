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

public class usersFragment extends Fragment {
    Button btnadduser,viewusers;
    private MySQLiteOpenHelper helper;
    private UserDataSource uds;
    private View v;
    ListView list;
    private EditText search;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_users,container,false);

        btnadduser = v.findViewById(R.id.btn_adduser);
        viewusers = v.findViewById(R.id.btn_viewusers);
        helper = new MySQLiteOpenHelper(getContext(),"Library_DB",null,1);
        uds = new UserDataSource(helper);
        list = (ListView) v.findViewById(R.id.UsersListView);
        search = v.findViewById(R.id.btn_searchusers);

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
        btnadduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new NewUserFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        viewusers.setOnClickListener(new View.OnClickListener() {
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
                uds.deleteUserById(id);
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
                TextView mobil = (TextView) info.targetView.findViewById(R.id.tvm_phone);
                TextView email = (TextView) info.targetView.findViewById(R.id.tvm_email);
                TextView access = (TextView) info.targetView.findViewById(R.id.tvm_access);
                TextView username = (TextView) info.targetView.findViewById(R.id.tvm_username);
                TextView password = (TextView) info.targetView.findViewById(R.id.tvm_password);



                int id = Integer.parseInt(txt_id.getText().toString());
                String fname = firstname.getText().toString();
                String lname = lastname.getText().toString();
                String Mobile = mobil.getText().toString();
                String Email = email.getText().toString();
                String Access = access.getText().toString();
                String Username = username.getText().toString();
                String Password = password.getText().toString();


                Bundle bundle = new Bundle();
                bundle.putInt("ID", id);
                bundle.putString("fname",fname);
                bundle.putString("lname",lname);
                bundle.putString("phone",Mobile);
                bundle.putString("email",Email);
                bundle.putString("access",Access);
                bundle.putString("username",Username);
                bundle.putString("password",Password);


                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                UpdateUserFragment updateUserFragment = new UpdateUserFragment();
                updateUserFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, updateUserFragment);
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
            UserAdapter adapter = new UserAdapter(getActivity(), R.layout.users_item_layout, uds.getUsersData(s));
            list.setAdapter(adapter);
        }
        catch (Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
