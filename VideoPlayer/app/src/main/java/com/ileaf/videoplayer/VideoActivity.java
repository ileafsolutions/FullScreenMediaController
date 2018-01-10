package com.ileaf.videoplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;


import com.ileaf.fullscreenmediacontrollerlibrary.FullScreenMediaController;
import com.ileaf.fullscreenmediacontrollerlibrary.FullScreenVideoCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by e203 on 2/11/17.
 */

public class VideoActivity extends Activity implements FullScreenVideoCallBack {

    String video_url = "";
    int video_position;
    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    private FullScreenMediaController mediaControls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_frame);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        video_url = bundle.getString("video_url");
        video_position = bundle.getInt("video_position");
        progressBar1.setVisibility(View.VISIBLE);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //videoView.setLayoutParams(new FrameLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels));
        try {
            //set the media controller in the VideoView
            if (mediaControls == null) {
                mediaControls = new FullScreenMediaController(this,true, this);
            }
            videoView.setMediaController(mediaControls);
            //set the uri of the video to be played
            videoView.setVideoPath(video_url);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        videoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                progressBar1.setVisibility(View.GONE);
                //if we have a position on savedInstanceState, the video playback should start from here
                videoView.seekTo(video_position);
                videoView.start();
            }
        });
    }

    @Override
    public void navigateToFullScreenVideoScreen() {
       finish();
    }
}
