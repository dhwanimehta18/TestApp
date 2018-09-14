package com.example.testapp.testapp.Royal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.testapp.testapp.R;

public class RoyalActivity1 extends AppCompatActivity implements View.OnClickListener {

    Button btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    ImageView btn1;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_royal1);

        btn1 = (ImageView) findViewById(R.id.btn1);

        checkBox.setOnClickListener(RoyalActivity1.this);
        btn9.setOnClickListener(RoyalActivity1.this);
        btn6.setOnClickListener(RoyalActivity1.this);
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()){
            case R.id.checkBox:
                if (checkBox.isChecked()){
                    checkBox.setText("Checked");
                }else {
                    checkBox.setText("Unchecked");
                }
                break;

            case R.id.btn9:
                if (checkBox.isShown()){
                    checkBox.setVisibility(View.INVISIBLE);
                    btn9.setText("Hide");
                }else {
                    checkBox.setVisibility(View.VISIBLE);
                    btn9.setText("Unhide");
                }
                break;

            case R.id.btn6:
                Intent intent = new Intent(RoyalActivity1.this,RoyalActivity2.class);
                startActivity(intent);
        }*/
    }
}
