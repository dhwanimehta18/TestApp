package com.example.testapp.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SimpleAdapter extends AppCompatActivity implements View.OnClickListener {

    EditText etTextNo ;
    Button btnAddNo ;
    ListView lstViewNumberAdded;
    private int str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter);

        etTextNo = (EditText) findViewById(R.id.etTextNo) ;
        btnAddNo = (Button) findViewById(R.id.btnAddNo) ;
        lstViewNumberAdded = (ListView) findViewById(R.id.lstViewNumberAdded);

        btnAddNo.setOnClickListener(SimpleAdapter.this);
    }

    @Override
    public void onClick(View v) {
        //str = Integer.parseInt(etTextNo.getText().toString());
        str = Integer.parseInt(etTextNo.getText().toString());
        ArrayList<Integer> arrayList = new ArrayList<>() ;

        for (int i = 0 ; i<str ; i++){
            arrayList.add(i+1) ;
        }

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(SimpleAdapter.this,R.layout.simple_list_item,arrayList) ;
        lstViewNumberAdded.setAdapter(arrayAdapter);
    }
}
