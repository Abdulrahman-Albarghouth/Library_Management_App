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

public class BooksAdapter extends ArrayAdapter {
    private List<Book> list;
    private Context context;
    private int resId;
    private MySQLiteOpenHelper helper;
    private CategoryDataSource cds;
    private AuthorDataSource ads;

    public BooksAdapter(@NonNull Context context, int resource, @NonNull List list) {
        super(context, resource, list);
        this.list = list;
        this.context = context;
        this.resId = resource;

        helper = new MySQLiteOpenHelper(getContext(),"Library_DB",null,1);
        ads = new AuthorDataSource(helper);
        cds = new CategoryDataSource(helper);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resId, parent, false);
        TextView id = convertView.findViewById(R.id.tvm_ID);
        TextView category = convertView.findViewById(R.id.tvm_category);
        TextView author = convertView.findViewById(R.id.tvm_author);
        TextView bookname = convertView.findViewById(R.id.tvm_bookname);
        TextView relase = convertView.findViewById(R.id.tvm_release);
        TextView link = convertView.findViewById(R.id.tvm_links);
        TextView about = convertView.findViewById(R.id.tvm_aboutbook);
        Category c = cds.getCategoryById(list.get(position).getC_id());
        Author a = ads.getAuthorById(list.get(position).getAu_id());

        id.setText(list.get(position).getB_id() +"");
        category.setText(c.getCatName());
        author.setText(a.getFirstName() + " "+ a.getLastName());
        bookname.setText(list.get(position).getBookName() +"");
        relase.setText(list.get(position).getReleaseDate() +"");
        link.setText(list.get(position).getLink() +"");
        about.setText(list.get(position).getAbout() +"");

        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
