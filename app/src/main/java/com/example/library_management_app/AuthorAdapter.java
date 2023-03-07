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

public class AuthorAdapter extends ArrayAdapter {

    private List<Author> list;
    private Context context;
    private int resId;

    public AuthorAdapter(@NonNull Context context, int resource, @NonNull List list) {
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
        TextView birth = convertView.findViewById(R.id.tvm_birth);
        TextView death = convertView.findViewById(R.id.tvm_death);
        TextView about = convertView.findViewById(R.id.tvm_about);


        id.setText(list.get(position).getId() +"");
        firtname.setText(list.get(position).getFirstName());
        lastname.setText(list.get(position).getLastName()+"");
        birth.setText(list.get(position).getBirthDate());
        death.setText(list.get(position).getDeathDate()+"");
        about.setText(list.get(position).getAbout()+"");
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
