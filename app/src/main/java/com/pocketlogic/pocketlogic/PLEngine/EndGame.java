package com.pocketlogic.pocketlogic.PLEngine;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;
import com.pocketlogic.pocketlogic.R;

public class EndGame {

    private ImageView endGame_icon;
    private Activity activity;

    public EndGame(Activity activity){
        this.activity = activity;
        this.endGame_icon = activity.findViewById(R.id.EndScreen_Result);
    }

    public void hideScreen(){
        this.activity.findViewById(R.id.EndScreen_animation).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer11_EndScreen_BG).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer11_EndScreen).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer11_EndScreen_RET).setVisibility(View.INVISIBLE);
    }

    public void showScreen(boolean gameResult) {
        this.activity.findViewById(R.id.Layer11_EndScreen_BG).setVisibility(View.VISIBLE);
        this.activity.findViewById(R.id.Layer11_EndScreen).setVisibility(View.VISIBLE);
        this.activity.findViewById(R.id.Layer11_EndScreen_RET).setVisibility(View.VISIBLE);

        if (gameResult)
            endGame_icon.setImageResource(R.drawable.missionpass);
        else {
            endGame_icon.setImageResource(R.drawable.missionfailed);

            this.activity.findViewById(R.id.EndScreen_animation).setVisibility(View.VISIBLE);

            VideoView videoview = (VideoView) this.activity.findViewById(R.id.EndScreen_animation);
            Uri uri = Uri.parse("android.resource://"+this.activity.getPackageName()+"/"+ R.raw.explode);
            videoview.setVideoURI(uri);
            videoview.start();

        }
    }

}
