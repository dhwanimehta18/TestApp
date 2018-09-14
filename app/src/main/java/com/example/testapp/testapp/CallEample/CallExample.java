package com.example.testapp.testapp.CallEample;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapp.testapp.PhoneBook;
import com.example.testapp.testapp.R;
public class CallExample extends AppCompatActivity implements View.OnClickListener {
    EditText etNumber;
    Button btnDial;
    Button btnCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_example);
        etNumber = (EditText) findViewById(R.id.etNumber);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnDial = (Button) findViewById(R.id.btnDial);
        btnDial.setOnClickListener(CallExample.this);
        btnCall.setOnClickListener(CallExample.this);
    }
    @Override
    public void onClick(View v) {
        String getNumber = etNumber.getText().toString();
        Uri data = Uri.parse("tel://" + getNumber);
        switch (v.getId()) {
            case R.id.btnDial:
                showDial();
                break;
            case R.id.btnCall:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(data);
                Log.i("number",getNumber);
                Log.i("callIntent", String.valueOf(callIntent));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.i("","Permission Denied");
                    return;
                }
                startActivity(callIntent);
                break;
        }
    }
    private void showDial() {
        String getNumber = etNumber.getText().toString();
        Uri uri = Uri.parse("tel://" + getNumber);
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(uri);
        startActivity(dialIntent);
    }
}