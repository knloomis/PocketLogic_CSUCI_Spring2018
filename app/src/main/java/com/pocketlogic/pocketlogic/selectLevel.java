package com.pocketlogic.pocketlogic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pocketlogic.pocketlogic.PocketLogic.LevelManager;

public class selectLevel extends AppCompatActivity {
    int numLevels = 6;

    Context context;
    ImageView imageCard;
    Button next;
    Button prev;

    int curr_level_highlighted;
//POTENTIAL ISSUE: current version has creating possibly infinite stack of activities, since return not "finish" the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent menuIntent = getIntent();
        //Bundle extras = getIntent().getExtras();
        if(menuIntent.getExtras() != null){
            //something to set curr_level_selected to num passed
            curr_level_highlighted = menuIntent.getExtras().getInt("levelNum");
            if(curr_level_highlighted == -1){
                curr_level_highlighted = 0;
            }else if(curr_level_highlighted > numLevels){
                curr_level_highlighted = numLevels;
            }
        }else{
            curr_level_highlighted = 0;
        }
        imageCard = findViewById(R.id.level_card);
        imageCard.setImageResource(getCardDrawable(curr_level_highlighted));

        next = (Button) findViewById(R.id.nextLevelButton);
        prev = (Button) findViewById(R.id.prevLevelButton);

        setButtonsVisibility();

        Log.d("mine","selectLevel Triggered");

        //IMPORTANT!
        //A lot of function is requiring context, hence I am caching it locally.
        this.context = this;
        addListeners();
    }

    public void addListeners() {
        final Context context = this;

        /*

        Button btn_select= (Button) this.findViewById(R.id.btn_main_select);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mine","onClick Triggered");

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked

                                //Retrieve Level names
                                final LevelManager levelManager = new LevelManager();

                                //String Arraylist -> CharSequences Convertion
                                CharSequence[] levels = levelManager.getLevelNames().toArray(new CharSequence[levelManager.getLevelNames().size()]);
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                //Feed content to selection dialog
                                builder.setTitle("Level Selection");
                                builder.setItems(levels, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //HIJACK IT FROM HERE!
                                        //selectedLevelName will be a level name declared in levelManager
                                        //ex. "Connor's random level (1 Switch)"
                                        //Pass level name with a intent to render it!
                                        String selectedLevelName = levelManager.getLevelNames().get(which);

                                        Intent intent = new Intent(context, GameScene.class);
                                        intent.putExtra("levelName", selectedLevelName);
                                        startActivity(intent);
                                    }
                                });
                                builder.show();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                Intent intent = new Intent(context, selectLevel2.class);
                                startActivity(intent);
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to use the PLEngine?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        */

        Button btn_help= (Button) this.findViewById(R.id.btn_main_help);
        btn_help.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, help.class);
                startActivity(intent);
            }
        });
    }

    public int getCardDrawable(int numInList){
        switch(numInList){
            case 0: return R.drawable.level0_image;
            case 1: return R.drawable.level1_image;
            case 2: return R.drawable.level2_image;
            case 3: return R.drawable.level3_image;
            case 4: return R.drawable.level4_image;
            case 5: return R.drawable.level5_image;
            case 6: return R.drawable.level6_image;
            case 7: return R.drawable.level7_image;
            case 8: return R.drawable.level8_image;
            case 9: return R.drawable.level9_image;
            case 10: return R.drawable.level10_image;
            default: return R.drawable.mascot_0;
        }
    }

    public void levelSelect(View cardView){
        //String levelName = "Level " + curr_level_highlighted;
        Intent play;
        if(curr_level_highlighted != 0){
            play = new Intent(context, selectLevel2.class);
            play.putExtra("levelNum", curr_level_highlighted);
        }else{
            play = new Intent(context, TutorialTest.class);
        }
        startActivity(play);
        overridePendingTransition(0,0);
        play.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    public void showNextLevel(View cardView){
        if(curr_level_highlighted < numLevels){
            curr_level_highlighted++;
            imageCard.setImageResource(getCardDrawable(curr_level_highlighted));
            setButtonsVisibility();
        }
    }

    public void showPreviousLevel(View cardView){
        if(curr_level_highlighted > 0){
            curr_level_highlighted--;
            imageCard.setImageResource(getCardDrawable(curr_level_highlighted));
            setButtonsVisibility();
        }
    }

    public void hidePrevButton(){
        prev.setVisibility(View.GONE);
    }

    public void hideNextButton(){
        next.setVisibility(View.GONE);
    }

    public void setButtonsVisibility(){
        if(curr_level_highlighted == 0){
            hidePrevButton();
        }else if(curr_level_highlighted == numLevels){
            hideNextButton();
        }else{
            prev.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        }
    }
}
