package com.ileaf.videoplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.ileaf.fullscreenmediacontrollerlibrary.FullScreenMediaController;
import com.ileaf.fullscreenmediacontrollerlibrary.FullScreenVideoCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FullScreenVideoCallBack {

    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    @BindView(R.id.iv_play_video)
    ImageView ivPlayVideo;
    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.fl_start_crunch)
    FrameLayout flStartCrunch;
    FullScreenMediaController mediaControls;
    int video_position;
    private static String VIDEO_URL = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ivPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivPlayVideo.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                progressBar1.setVisibility(View.VISIBLE);
                try {
                    //set the media controller in the VideoView
                    if (mediaControls == null) {
                        mediaControls = new FullScreenMediaController(MainActivity.this,false, MainActivity.this);
                    }
                    videoView.setMediaController(mediaControls);
                    //set the uri of the video to be played
                    videoView.setVideoPath(VIDEO_URL);
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
                        if (video_position == 0) {
                            videoView.start();
                        } else {
                            //if we come from a resumed activity, video playback will be paused
                            videoView.pause();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void navigateToFullScreenVideoScreen() {
        Intent intent = new Intent(this, VideoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("video_url", VIDEO_URL);
        bundle.putInt("video_position", videoView.getCurrentPosition());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
