package com.example.testapp.testapp.VideoExample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.testapp.testapp.R;

public class VideoPlay extends AppCompatActivity {

    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        videoView = (VideoView) findViewById(R.id.videoView);
        Bundle bundle = getIntent().getExtras();
        String video = bundle.getString("id");
        videoView.setVideoPath(video);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();
    }
}
