package com.example.testapp.testapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ColorMixer extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    SeekBar skbRed;
    SeekBar skbBlue;
    SeekBar skbGreen;

    TextView tvColor;
    TextView tvValue;

    RatingBar rtbColor;

    int red, blue, green, colorValue;
    private String color;
    String color_value = "color_value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_mixer);

        skbRed = (SeekBar) findViewById(R.id.skbRed);
        skbBlue = (SeekBar) findViewById(R.id.skbBlue);
        skbGreen = (SeekBar) findViewById(R.id.skbGreen);

        tvColor = (TextView) findViewById(R.id.tvColor);
        tvValue = (TextView) findViewById(R.id.tvValue);

        rtbColor = (RatingBar) findViewById(R.id.rtbColor);
        
        SharedPreferences pref = getSharedPreferences(color_value,Context.MODE_PRIVATE) ;
        int colorPref = pref.getInt(color, colorValue);;
        tvColor.setBackgroundColor(colorPref);

        skbRed.setOnSeekBarChangeListener(ColorMixer.this);
        skbBlue.setOnSeekBarChangeListener(ColorMixer.this);
        skbGreen.setOnSeekBarChangeListener(ColorMixer.this);

        rtbColor.setOnRatingBarChangeListener(ColorMixer.this);
        tvColor.setOnClickListener(ColorMixer.this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.skbRed:
                red = progress;
                break;
            case R.id.skbBlue:
                blue = progress;
                break;
            case R.id.skbGreen:
                green = progress;
                break;
        }
        colorValue = Color.rgb(red, blue, green);
        tvColor.setBackgroundColor(colorValue);
        tvColor.setText("Your Color :" + red + ":" + blue + ":" + green);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        Toast.makeText(getApplicationContext(), "" + rating, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        tvValue.setText("" + colorValue);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.om_options, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences prefs = getSharedPreferences(color_value, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(color, colorValue);
        editor.commit();
        return super.onOptionsItemSelected(item);
    }
}