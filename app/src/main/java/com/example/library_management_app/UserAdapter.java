package com.example.library_management_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserAdapter extends ArrayAdapter {
    private List<User> list;
    private Context context;
    private int resId;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List list) {
        super(context, resource, list);
        this.list = list;
        this.context = context;
        this.resId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resId, parent, false);
        TextView id = convertView.findViewById(R.id.tvm_ID);
        TextView firtname = convertView.findViewById(R.id.tvm_firstname);
        TextView lastname = convertView.findViewById(R.id.tvm_lastname);
        TextView email = convertView.findViewById(R.id.tvm_email);
        TextView phone = convertView.findViewById(R.id.tvm_phone);
        TextView username = convertView.findViewById(R.id.tvm_username);
        TextView accesstype = convertView.findViewById(R.id.tvm_access);
        TextView password = convertView.findViewById(R.id.tvm_password);


        id.setText(list.get(position).getId() +"");
        firtname.setText(list.get(position).getFirstName());
        lastname.setText(list.get(position).getLastName()+"");
        email.setText(list.get(position).getEmail()+"");
        phone.setText(Long.toString(list.get(position).getPhoneNumber()));
        username.setText(list.get(position).getUserName()+"");
        accesstype.setText(Integer.toString(list.get(position).getAccessType()));
        password.setText(list.get(position).getPassword()+"");


        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
