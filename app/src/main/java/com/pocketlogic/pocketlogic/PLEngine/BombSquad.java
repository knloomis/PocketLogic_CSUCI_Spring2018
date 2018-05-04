package com.pocketlogic.pocketlogic.PLEngine;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pocketlogic.pocketlogic.R;

/*======================================   USER MANUAL    ==========================================
    Usage: Provides accessor to bomb elements

    Declaration
    BombSquad bombSquad = new BombSquad(this);

    Available Functions

    void setBombTimer(int secs)                                     <- Set seconds to bomb (Display)
    void hideBombSquadPrompt()                                      <- Hide the prompt
    void showBombSquadPrompt(int secs)                              <- Show prompt with seconds
    void enableBomb()                                               <- Make it looks activated
    void disableBomb()                                              <- Make it looks redacted
*/

public class BombSquad {

    //===============================       PRIVATE MEMBERS      ===================================
    private Activity activity;
    private TextView countDown;
    private TextView timerInfo;
    private ImageView bomb;

    //=====================================    CONSTRUCTOR    ======================================
    public BombSquad(Activity activity) {
        this.activity = activity;
        countDown = (TextView)activity.findViewById(R.id.bomb_countdown);
        timerInfo = (TextView) activity.findViewById(R.id.bombSquadInfo_seconds);
        bomb = (ImageView) activity.findViewById(R.id.bomb);
    }

    //===================================   PUBLIC FUNCTIONS    ====================================
    public void setBombTimer(int secs){
        this.countDown.setText(secondsToString(secs));
    }

    public void showBombSquadPrompt(int secs){
        String displaySeconds = secondsToString(secs);
        this.timerInfo.setText(displaySeconds);
        this.activity.findViewById(R.id.Layer9_bombSquadInfo).setVisibility(View.VISIBLE);
        this.activity.findViewById(R.id.Layer10_bombSquad_RET).setVisibility(View.VISIBLE);
    }

    public void hideBombSquadPrompt(){
        this.activity.findViewById(R.id.Layer9_bombSquadInfo).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer10_bombSquad_RET).setVisibility(View.INVISIBLE);
    }

    public void enableBomb(){
        this.bomb.setImageResource(R.drawable.bomb);
    }

    public void disableBomb(){
        this.bomb.setImageResource(R.drawable.bomb_safe);
        this.countDown.setText("");
    }

    //===================================   PRIVATE FUNCTIONS    ===================================

    //========================================
    // Seconds to a bomb string
    // 1 -> 00:01
    // 100000 - > 99:99
    private String secondsToString(int secs){
        int minutes = secs / 60;
        int seconds = secs % 60;
        if (minutes>99)
            minutes=99;

        String minutesStr = Integer.toString(minutes);
        String secondsStr = Integer.toString(seconds);
        if (minutesStr.length() == 1) {
            minutesStr = "0" + minutesStr;
        }
        if (secondsStr.length() == 1) {
            secondsStr = "0" + secondsStr;
        }
        return minutesStr + ":" + secondsStr;
    }

}
