package com.example.library_management_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserHomeFragment extends Fragment {

    private TextView fullname;
    private Bundle bundle;
    private MySQLiteOpenHelper helper;
    private UserDataSource uds;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_home, container, false);
        fullname = v.findViewById(R.id.tv_fname_main);

        helper = new MySQLiteOpenHelper(getContext(),"Library_DB",null,1);
        uds = new UserDataSource(helper);
        bundle = getArguments();
        int ID = bundle.getInt("ID");
        User user = uds.getUserById(ID);
        fullname.setText(getString(R.string.welcome)+" "+user.getFirstName().toUpperCase()+" "+user.getLastName().toUpperCase() +" TO OUR LIBRARY");

        return v;
    }
}
