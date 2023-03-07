package com.example.library_management_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class profileFragment extends Fragment {
    private TextView Email ,fullname,ID,ContactNo,access;
    private MySQLiteOpenHelper helper;
    private UserDataSource uds;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_profile,container,false);

        try {

            ID = v.findViewById(R.id.user_id);
            Email = v.findViewById(R.id.tvp_email);
            fullname = v.findViewById(R.id.emp_fullname);
            ContactNo = v.findViewById(R.id.emp_contactno);
            access = v.findViewById(R.id.p_accesstype);
            helper = new MySQLiteOpenHelper(getActivity(), "Library_DB", null, 1);
            uds = new UserDataSource(helper);
            Bundle bundle = getArguments();
            int id = bundle.getInt("ID");
            User user;
            user = uds.getUserById(id);
            fullname.setText(user.getFirstName().toUpperCase()+" "+ user.getLastName().toUpperCase());
            ID.setText(Integer.toString(user.getId()));
            Email.setText(user.getEmail());
            ContactNo.setText(Long.toString(user.getPhoneNumber()));
            if(user.getAccessType() == 1 ){
                access.setText(getString(R.string.Admin));
            }
            else {
                access.setText(getString(R.string.User));
            }
        }
        catch (Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }


        return v;

    }
}