package com.example.testapp.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class CustomAdapter extends AppCompatActivity {

    ListView lstView1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adapter);

        lstView1 = (ListView) findViewById(R.id.lstView1);

        ArrayList<Student_details> array_list = new ArrayList<Student_details>();
        array_list.add(new Student_details(04,"Dhwani","Ahmedabad"));
        array_list.add(new Student_details(05,"Komal","Ahmedabad"));
        array_list.add(new Student_details(06,"Himani","Ahmedabad"));

        StudentAdapter adapter = new StudentAdapter(CustomAdapter.this,array_list) ;
        lstView1.setAdapter(adapter);


    }
}
