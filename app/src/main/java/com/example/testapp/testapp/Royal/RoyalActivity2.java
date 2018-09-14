package com.example.testapp.testapp.Royal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.testapp.testapp.R;

public class RoyalActivity2 extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener, RadioGroup.OnCheckedChangeListener {

    RadioButton rdb1,rdb2,rdb3;
    Button btnNext;
    //TextView tvChoice;
    RatingBar ratingbar;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_royal2);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rdb1 = (RadioButton) findViewById(R.id.rdb1);
        rdb2 = (RadioButton) findViewById(R.id.rdb2);
        rdb3 = (RadioButton) findViewById(R.id.rdb3);

        btnNext = (Button) findViewById(R.id.btnNext);

        //tvChoice = (TextView) findViewById(R.id.tvChoice);
        //tvRate = (TextView) findViewById(R.id.tvRate);

        ratingbar = (RatingBar) findViewById(R.id.ratingbar);

        ratingbar.setOnRatingBarChangeListener(RoyalActivity2.this);
        radioGroup.setOnCheckedChangeListener(RoyalActivity2.this);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        Toast.makeText(RoyalActivity2.this, ""+rating, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rdb1:
                Toast.makeText(RoyalActivity2.this,""+rdb1.getText(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.rdb2:
                Toast.makeText(RoyalActivity2.this,""+rdb2.getText(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.rdb3:
                Toast.makeText(RoyalActivity2.this,""+rdb3.getText(),Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
