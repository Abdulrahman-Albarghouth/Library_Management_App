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

public class categoryFragment extends Fragment {
    Button btnAddCategory,ViewCategories;
    private MySQLiteOpenHelper helper;
    private CategoryDataSource cds;
    private View v;
    ListView list;
    private EditText search;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_category,container,false);

        btnAddCategory = v.findViewById(R.id.btn_addcategory);
        ViewCategories = v.findViewById(R.id.btn_showcategories);
        helper = new MySQLiteOpenHelper(getContext(),"Library_DB",null,1);
        cds = new CategoryDataSource(helper);
        list = (ListView) v.findViewById(R.id.categoriesListView);
        search = v.findViewById(R.id.et_searchcategories);
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
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new NewCategoryFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        ViewCategories.setOnClickListener(new View.OnClickListener() {
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
                cds.deleteCategoryById(id);
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
                TextView txt_name = (TextView) info.targetView.findViewById(R.id.tvm_Categoryname);
                int id = Integer.parseInt(txt_id.getText().toString());
                String name = txt_name.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putInt("ID", id);
                bundle.putString("Name",name);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                UpdateCategoryFragment updateCategoryFragment = new UpdateCategoryFragment();
                updateCategoryFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, updateCategoryFragment);
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
            CategoryAdapter adapter = new CategoryAdapter(getActivity(), R.layout.category_item_layout, cds.getCategoryData(s));
            list.setAdapter(adapter);
        }
        catch (Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
