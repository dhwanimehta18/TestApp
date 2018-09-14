package com.example.testapp.testapp.VideoExample;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.testapp.testapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.jar.Manifest;

import static android.os.Environment.DIRECTORY_MOVIES;

public class VideoExample extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ListView lstVideoListView;
    Button btnVideo;

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_example);

        lstVideoListView = (ListView) findViewById(R.id.lstVideoListView);
        btnVideo = (Button) findViewById(R.id.btnVideo);

        btnVideo.setOnClickListener(VideoExample.this);
        arrayList = new ArrayList<>();
        lstVideoListView.setOnItemClickListener(VideoExample.this);
    }

    @Override
    public void onClick(View v) {
        adapter = new ArrayAdapter<String>(VideoExample.this,android.R.layout.simple_list_item_1,arrayList);
        lstVideoListView.setAdapter(adapter);
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.Media.DISPLAY_NAME,MediaStore.Video.Media.DATA,MediaStore.Video.Media.ALBUM};

        if(ActivityCompat.shouldShowRequestPermissionRationale(VideoExample.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            return;
        }

        Cursor res = getContentResolver().query(uri, projection, null, null, null);

        if(res != null){
            while (res.moveToNext()){
                String path = res.getString(res.getColumnIndex(MediaStore.Video.Media.DATA));
                String name = res.getString(res.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                String album = res.getString(res.getColumnIndex(MediaStore.Video.Media.ALBUM));
                arrayList.add(path);
            }
        }
     }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String videoId = adapter.getItem(position);
        Intent i = new Intent(VideoExample.this,VideoPlay.class);
        i.putExtra("id",videoId);
        startActivity(i);
    }
}
