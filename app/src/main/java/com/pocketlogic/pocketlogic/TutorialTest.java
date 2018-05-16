package com.pocketlogic.pocketlogic;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

public class TutorialTest extends AppCompatActivity {
    int num_animations = 10;
    int curr_num = 1;
    VideoView videoView;
    ImageView forward;
    ImageView backward;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_test);

        videoView = (VideoView) findViewById(R.id.tutorialTestVideoView);
        forward = (ImageView) findViewById(R.id.tutorialTestForward);
        backward = (ImageView) findViewById(R.id.tutorialTestBack);

        setButtonsVisibility();
        animate();

        /*

            VideoView videoview = (VideoView) this.activity.findViewById(R.id.EndScreen_animation);
            Uri uri = Uri.parse("android.resource://"+this.activity.getPackageName()+"/"+ R.raw.explode);
            videoview.setVideoURI(uri);
            videoview.start();
         */
    }

    private int getAnimResource(int num){
        switch(num){
            case 1: return R.raw.explode;
            case 2: return R.raw.explode;
            case 3: return R.raw.explode;
            case 4: return R.raw.explode;
            case 5: return R.raw.explode;
            case 6: return R.raw.explode;
            case 7: return R.raw.explode;
            case 8: return R.raw.explode;
            case 9: return R.raw.explode;
            case 10: return R.raw.explode;
            default: return R.raw.explode;
        }
    }

    public void getNextAnimation(View view){
        if(curr_num != num_animations){
            curr_num++;
            setButtonsVisibility();
            animate();
        }
    }

    public void getPrevAnimation(View view){
        if(curr_num != 1){
            curr_num--;
            setButtonsVisibility();
            animate();
        }
    }

    private void setButtonsVisibility(){
        if(curr_num == 1){
            backward.setVisibility(View.GONE);
        }else if(curr_num == num_animations){
            forward.setVisibility(View.GONE);
        }else{
            backward.setVisibility(View.VISIBLE);
            forward.setVisibility(View.VISIBLE);
        }
    }

    public void quitTutorialTest(View view){
        videoView.clearAnimation();
        finish();
    }

    private void animate(){
        int resourceVal = getAnimResource(curr_num);
        uri = Uri.parse("android.resource://"+this.getPackageName()+"/"+ resourceVal);
        videoView.clearAnimation();
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnCompletionListener ( new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
    }
}
