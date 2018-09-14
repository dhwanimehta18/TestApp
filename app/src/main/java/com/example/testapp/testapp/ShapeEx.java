package com.example.testapp.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShapeEx extends AppCompatActivity implements View.OnClickListener {
    Button btnSquare;
    Button btnCircle;
    Button btnBorderedSquare;
    ImageView imgShape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_ex);
        btnSquare = (Button) findViewById(R.id.btnSquare);
        btnCircle = (Button) findViewById(R.id.btnCircle);
        btnBorderedSquare = (Button) findViewById(R.id.btnBorderedSquare);
        imgShape = (ImageView) findViewById(R.id.imgShape);
        btnSquare.setOnClickListener(ShapeEx.this);
        btnCircle.setOnClickListener(ShapeEx.this);
        btnBorderedSquare.setOnClickListener(ShapeEx.this);
        registerForContextMenu(imgShape);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSquare:
                imgShape.setImageResource(R.drawable.shape_square);
                break;
            case R.id.btnCircle:
                imgShape.setImageResource(R.drawable.shape_circle);
                break;
            case R.id.btnBorderedSquare:
                imgShape.setImageResource(R.drawable.shape_bordered_square);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Rotate");
        menu.add(0, v.getId(), 0, "Alpha");
        menu.add(0, v.getId(), 0, "Scale");
        menu.add(0, v.getId(), 0, "Translate");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)
                findViewById(R.id.tvCustomToastLayout));
        TextView tvCustomToast;
        tvCustomToast = (TextView) layout.findViewById(R.id.tvCustomToast);
        Toast toast = new Toast(getApplicationContext());
        Animation animation = null;
        if (item.getTitle() == "Rotate") {
            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
            imgShape.startAnimation(animation);
            tvCustomToast.setText("Rotate");
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
        if (item.getTitle() == "Alpha") {
            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
            imgShape.startAnimation(animation);
            tvCustomToast.setText("Alpha");
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
        if (item.getTitle() == "Scale") {
            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
            imgShape.startAnimation(animation);
            tvCustomToast.setText("Scale");
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
        if (item.getTitle() == "Translate") {
            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
            imgShape.startAnimation(animation);
            tvCustomToast.setText("Translate");
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
        return super.onContextItemSelected(item);
    }

}
