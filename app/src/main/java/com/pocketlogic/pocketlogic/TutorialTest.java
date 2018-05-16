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
    int num_animations = 3;
    int curr_num = 1;
    VideoView videoView;
    ImageView forward;
    ImageView backward;
    ImageView imageCard;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_test);
        int current_screen = 1;


        videoView = (VideoView) findViewById(R.id.tutorialTestVideoView);
        forward = (ImageView) findViewById(R.id.tutorialTestForward);
        backward = (ImageView) findViewById(R.id.tutorialTestBack);


       // imageCard.setImageResource(getCardDrawable(current_screen));

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
        imageCard = findViewById(R.id.caption);

        switch(num){
            case 1:
                imageCard.setImageResource(R.drawable.caption_1);
                return R.raw.step1;
            case 2:
                imageCard.setImageResource(R.drawable.caption_2);
                return R.raw.step2;
            case 3:
                imageCard.setImageResource(R.drawable.caption_3);
                return R.raw.step3;
            /*
            case 4: return R.raw.explode;
            case 5: return R.raw.explode;
            case 6: return R.raw.explode;
            case 7: return R.raw.explode;
            case 8: return R.raw.explode;
            case 9: return R.raw.explode;
            case 10: return R.raw.explode;
            */
            default: return R.raw.explode;
        }
    }

    public int getCardDrawable(int numInList){
        imageCard = findViewById(R.id.caption);

        switch(numInList){
            case 0:
                imageCard.setImageResource(R.drawable.caption_1);
                return R.drawable.caption_1;
            case 1:
                imageCard.setImageResource(R.drawable.caption_2);
                return R.drawable.caption_2;
            case 2:
                imageCard.setImageResource(R.drawable.caption_3);
                return R.drawable.caption_3;
            default: return R.drawable.mascot_0;
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
