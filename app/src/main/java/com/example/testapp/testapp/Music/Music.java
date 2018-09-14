package com.example.testapp.testapp.Music;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testapp.testapp.ImageDetails.ImageDetails;
import com.example.testapp.testapp.R;

import java.util.ArrayList;

public class Music extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ListView lstMusic;
    Button btnShowimage;
    ImageView imgShow;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        btnShowimage = (Button) findViewById(R.id.btnShowimage);
        lstMusic = (ListView) findViewById(R.id.lstMusic);

        btnShowimage.setOnClickListener(Music.this);
        arrayList = new ArrayList<>();
        lstMusic.setOnItemClickListener(Music.this);
    }

    @Override
    public void onClick(View v) {
        adapter = new ArrayAdapter<String>(Music.this,android.R.layout.simple_list_item_1,arrayList);
        //adapter = new ArrayAdapter<String>(Music.this,R.layout.musiclists,arrayList);
        lstMusic.setAdapter(adapter);

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.COMPOSER};

        if (ActivityCompat.shouldShowRequestPermissionRationale(Music.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return;
        }

        Cursor res = getContentResolver().query(uri, projection, null, null, null);

        if(res != null){
            while (res.moveToNext()){
                String path = res.getString(res.getColumnIndex(MediaStore.Audio.Media.DATA));
                String album = res.getString(res.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String displayName = res.getString(res.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String composer = res.getString(res.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                arrayList.add(path);
                Log.i("Album",album);
                Log.i("DisplayName",displayName);
                Log.i("Compser",composer);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String imageItem = adapter.getItem(position);
        Toast.makeText(Music.this,imageItem,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Music.this,MainActivity.class);
        intent.putExtra("id",imageItem);
        startActivity(intent);
    }
}
