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

public class CategoryAdapter extends ArrayAdapter {
    private List<Category> list;
    private Context context;
    private int resId;

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List list) {
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
        TextView categoryname = convertView.findViewById(R.id.tvm_Categoryname);

        id.setText(list.get(position).getId() +"");
        categoryname.setText(list.get(position).getCatName());
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
