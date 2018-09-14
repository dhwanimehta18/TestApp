package com.example.testapp.testapp;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.File;

public class
FileExplorer extends AppCompatActivity {

    ListView lstFileExp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explorer);

        lstFileExp = (ListView) findViewById(R.id.lstFileExp);

        //File root = new File("/");
        File root = Environment.getRootDirectory() ;
        File[] fileList = root.listFiles() ;

        file_adapter adapter = new file_adapter(FileExplorer.this,fileList) ;
        lstFileExp.setAdapter(adapter);
    }
}
