package com.example.testapp.testapp.ImageDetails;
import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testapp.testapp.R;

import java.util.ArrayList;
public class ImageDetails extends AppCompatActivity implements
        View.OnClickListener, AdapterView.OnItemClickListener {
    Button btnImage;
    ListView lstImageDetails;
    private ArrayList<String> arrList;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        btnImage = (Button) findViewById(R.id.btnImage);
        lstImageDetails = (ListView) findViewById(R.id.lstImageDetails);
        btnImage.setOnClickListener(ImageDetails.this);
        arrList = new ArrayList<>();
        lstImageDetails.setOnItemClickListener(ImageDetails.this);
    }
    @Override
    public void onClick(View v) {
        //adapter = new ArrayAdapter<>(ImageDetails.this,R.layout.musiclists,arrList);
        adapter = new ArrayAdapter<>(ImageDetails.this,android.R.layout.simple_list_item_1,arrList);
        lstImageDetails.setAdapter(adapter);
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] columns = {MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA,MediaStore.Images.Media.SIZE};
        if (ActivityCompat.shouldShowRequestPermissionRationale(ImageDetails.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return;
        }
        Cursor res = getContentResolver().query(uri, columns, null, null, null);
        if(res != null){
            while (res.moveToNext()){
                String path = res.getString(res.getColumnIndex(MediaStore.Images.Media.DATA));
                String name = res.getString(res.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                String data = res.getString(res.getColumnIndex(MediaStore.Images.Media.DATA));
                String size = res.getString(res.getColumnIndex(MediaStore.Images.Media.SIZE));
                arrList.add(path);
                Log.i("Data",data);
                Log.i("name",name);
                Log.i("size",size);
            }
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String imageItem = adapter.getItem(position);
        Toast.makeText(ImageDetails.this,imageItem,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
        startActivity(intent);
    }
}