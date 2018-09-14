package com.example.testapp.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin0 on 2/10/2017.
 */

public class StudentAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Student_details> array_list;

    public StudentAdapter(Context context, ArrayList<Student_details> array_list) {
        this.context = context ;
        this.array_list = array_list ;
    }

    @Override
    public int getCount() {
        return array_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.student_info,null);

        TextView txtRollNo ;
        TextView txtName;
        TextView txtAddress ;

        txtAddress = (TextView) ll.findViewById(R.id.txtAddress);
        txtName = (TextView) ll.findViewById(R.id.txtName);
        txtRollNo = (TextView) ll.findViewById(R.id.txtRollNo);

        txtRollNo.setText(array_list.get(position).getRollNo()+"");
        txtName.setText(array_list.get(position).getName()+"");
        txtAddress.setText(array_list.get(position).getAddress()+"");

        return ll;
    }
}
