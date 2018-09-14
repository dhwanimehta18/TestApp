package com.example.testapp.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

/**
 * Created by admin0 on 2/9/2017.
 */
public class file_adapter extends BaseAdapter {
    private final Context fileExplorer;
    private final File[] fileList;

    public file_adapter(Context fileExplorer, File[] fileList) {
        this.fileExplorer = fileExplorer;
        this.fileList = fileList;
    }

    @Override
    public int getCount() {
        return fileList.length;
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
        LayoutInflater inflater = (LayoutInflater) fileExplorer.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.file_list_item, null);
        TextView tvFileName;
        ImageView imgFileIcon;
        tvFileName = (TextView) ll.findViewById(R.id.tvFileName);
        imgFileIcon = (ImageView) ll.findViewById(R.id.imgFileIcon);
        tvFileName.setText(fileList[position].getName());
        if (fileList[position].isFile()) {
            imgFileIcon.setVisibility(convertView.INVISIBLE);
        }
        return ll;
    }
}