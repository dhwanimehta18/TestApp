package com.example.testapp.testapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SnackBarDemo extends AppCompatActivity implements View.OnClickListener {

    LinearLayout snackBarLayout;
    Button btnSimpleSnackbar, btnActionCallback, btnCustomSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_bar_demo);


        btnSimpleSnackbar = (Button) findViewById(R.id.btnSimpleSnackbar);
        btnActionCallback = (Button) findViewById(R.id.btnActionCallback);
        btnCustomSnackbar = (Button) findViewById(R.id.btnCustomSnackbar);

        snackBarLayout = (LinearLayout) findViewById(R.id.snackBarLayout);


        btnSimpleSnackbar.setOnClickListener(this);
        btnActionCallback.setOnClickListener(this);
        btnCustomSnackbar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSimpleSnackbar:
                Snackbar.make(snackBarLayout, "Normal SnackBar", BaseTransientBottomBar.LENGTH_SHORT).show();
                break;

            case R.id.btnActionCallback:
                Snackbar.make(snackBarLayout, "Message is deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Snackbar.make(snackBarLayout, "Message restored", Snackbar.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            case R.id.btnCustomSnackbar:
                Snackbar snackbar = Snackbar.make(snackBarLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                snackbar.setActionTextColor(Color.RED);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);

                snackbar.show();

                break;
        }
    }
}
