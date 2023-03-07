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

public class UpdateCategoryFragment extends Fragment {
    EditText categoryname;
    private Button btnupdate;
    private MySQLiteOpenHelper helper;
    private CategoryDataSource cds;
    private Bundle bundle;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_update_category,container,false);
        categoryname = v.findViewById(R.id.et_categoryname);
        btnupdate = v.findViewById(R.id.btn_updatecategory);
        helper = new MySQLiteOpenHelper(getContext(),"Library_DB",null,1);
        cds = new CategoryDataSource(helper);
        bundle = getArguments();
        LoadCategoryData();
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (categoryname.getText().toString().isEmpty()) {
                        categoryname.setError(getString(R.string.Enter_Category_Name));
                        categoryname.requestFocus();
                        return;
                    }
                    int id = bundle.getInt("ID");
                    Category cs = new Category(categoryname.getText().toString().trim());
                    boolean result = cds.updateCategoryData(cs,id);
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
        builder.setMessage(R.string.Updated_Successfully);
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
    public void LoadCategoryData(){
        categoryname.setText(bundle.getString("Name"));
    }
}
