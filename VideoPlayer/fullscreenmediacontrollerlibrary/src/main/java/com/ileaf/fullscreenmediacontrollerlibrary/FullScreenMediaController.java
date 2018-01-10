package com.ileaf.fullscreenmediacontrollerlibrary;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;

/**
 * Created by e203 on 28/12/17.
 */

public class FullScreenMediaController extends MediaController{

    private ImageButton fullScreen;
    boolean isFullScreenExit;
    FullScreenVideoCallBack fullScreenVideoCallBack;

    public FullScreenMediaController(Context context,boolean isFullScreenExit, FullScreenVideoCallBack fullScreenVideoCallBack) {
        super(context);
        this.isFullScreenExit = isFullScreenExit;
        this.fullScreenVideoCallBack = fullScreenVideoCallBack;
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);
        fullScreen = new ImageButton (super.getContext());
        ViewCompat.setBackground(fullScreen, null);
        LayoutParams params =
                new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.rightMargin = 20;
        addView(fullScreen, params);
        if (!isFullScreenExit) {
            fullScreen.setImageResource(R.drawable.ic_full_screen);
        }
        else {
            fullScreen.setImageResource(R.drawable.ic_fullscreen_exit);
        }
        fullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               fullScreenVideoCallBack.navigateToFullScreenVideoScreen();
            }
        });
    }
}
