package com.example.testapp.testapp.Music;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.testapp.testapp.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer mp = new MediaPlayer();
        Bundle bundle = getIntent().getExtras();
        String path = bundle.getString("id");

        try {
            mp.setDataSource(path);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
