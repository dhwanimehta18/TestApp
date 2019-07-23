package com.example.testapp.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.testapp.testapp.CallEample.CallExample;
import com.example.testapp.testapp.DatabaseExample.DBExample;
import com.example.testapp.testapp.Royal.RoyalActivity1;
import com.example.testapp.testapp.VideoExample.VideoExample;

public class ListViewDemo extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_demo);
        lstView = (ListView) findViewById(R.id.lstView);
        lstView.setOnItemClickListener(ListViewDemo.this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent next = null;
        switch (position) {
            case 0:
                next = new Intent(ListViewDemo.this, ColorMixer.class);
                startActivity(next);
                break;
            case 1:
                next = new Intent(ListViewDemo.this, ShapeEx.class);
                startActivity(next);
                break;
            case 2:
                next = new Intent(ListViewDemo.this, FileExplorer.class);
                startActivity(next);
                break;
            case 3:
                next = new Intent(ListViewDemo.this, SimpleAdapter.class);
                startActivity(next);
                break;
            case 4:
                next = new Intent(ListViewDemo.this, CustomAdapter.class);
                startActivity(next);
                break;
            case 5:
                next = new Intent(ListViewDemo.this, CallExample.class);
                startActivity(next);
                break;
            case 6:
                next = new Intent(ListViewDemo.this, DBExample.class);
                startActivity(next);
                break;
            case 7:
                next = new Intent(ListViewDemo.this, VideoExample.class);
                startActivity(next);
                break;
            case 8:
                next = new Intent(ListViewDemo.this, RoyalActivity1.class);
                startActivity(next);
                break;
            case 9:
                next = new Intent(ListViewDemo.this, RecycleDemo.class);
                startActivity(next);
                break;
            case 10:
                next = new Intent(ListViewDemo.this, SnackBarDemo.class);
                startActivity(next);
        }
    }
}