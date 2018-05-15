package com.pocketlogic.pocketlogic;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pocketlogic.pocketlogic.PocketLogic.LevelManager;

import static com.pocketlogic.pocketlogic.selectLevel2.difficulty.EASY;
import static com.pocketlogic.pocketlogic.selectLevel2.difficulty.HARD;

/**
 * Created by Vitaliy Six on 2018/3/10.
 */

public class selectLevel2 extends AppCompatActivity {
    String levelName;
    int levelNum;
    final LevelManager levelManager = new LevelManager();

    Button hardButton;
    Button normalButton;

    enum difficulty{ EASY, HARD}

    difficulty mode = EASY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            //alt for if is 0, then do "tutorial"
            //levelName = intent.getExtras().getString("levelName");
            levelNum = intent.getExtras().getInt("levelNum");
            if(levelNum == 0){
                levelName = "Tutorial";
            }else{
                levelName = "Level " + levelNum;
            }

        }else{
            levelName = "Level K";
            levelNum = -1;
        }

        TextView title = findViewById(R.id.levelTitle);
        title.setText(levelName);

        hardButton = (Button) findViewById(R.id.hardModeButton);
        normalButton = (Button) findViewById(R.id.easyModeButton);

        setDisplayedGates();



        //addListeners();
    }

    public void addListeners() {
        final Context context = this;

        Button play= (Button) findViewById(R.id.btn_main2_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, gameplay.class);
                startActivity(intent);
            }
        });

        Button back= (Button) findViewById(R.id.btn_main2_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(context, selectLevel.class);
               // startActivity(intent);
                Intent intent = new Intent(context, selectLevel.class);
                intent.putExtra("levelNum", levelNum);
                startActivity(intent);
                overridePendingTransition(0,0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                //finish();
            }
        });
    }

    public void play(View imgView){
        //code to select level for director here
        final Context context = this;
        if(levelName.equals("Level K")){
            levelName = "Level 1";
        }else if(levelName.equals("Tutorial")){
            levelName = "Level 0";
        }

        if(mode == EASY){
            levelName += " Easy";

        }else{
            levelName += " Hard";
        }

        Intent intent = new Intent(context, GameScene.class);
        intent.putExtra("levelName", levelName);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    public void returnToMainMenu(View imgView){
        final Context context = this;

        Intent intent = new Intent(context, selectLevel.class);
        intent.putExtra("levelNum", levelNum);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    public void setHardMode(View imgView){
        //levelManager.getLevel(levelName).setTimeLimit(60);
        mode = HARD;

        setButtonActiveColors(hardButton);
        setButtonInactiveColors(normalButton);
    }

    public void setNormalMode(View imgView){
       // levelManager.getLevel(levelName).setTimeLimit(0);
        mode = EASY;

        setButtonInactiveColors(hardButton);
        setButtonActiveColors(normalButton);
    }

    public void setButtonInactiveColors(Button button){
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
       // button.setTextColor(getResources().getColor(R.color.white));
    }

    public void setButtonActiveColors(Button button){
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttonGreen)));
       // button.setTextColor(getResources().getColor(R.color.grey));
    }

    private void setDisplayedGates(){
        ImageView and = findViewById(R.id.and_gate);
        ImageView or = findViewById(R.id.or_gate);
        ImageView xor = findViewById(R.id.xor_gate);
        ImageView not = findViewById(R.id.not_gate);
        ImageView nor = findViewById(R.id.nor_gate);
        ImageView xnor = findViewById(R.id.xnor_gate);
        ImageView nand = findViewById(R.id.nand_gate);

        if(levelNum == 5){
            and.setImageResource(R.drawable.locked_grid);
            or.setImageResource(R.drawable.locked_grid);
            xor.setImageResource(R.drawable.locked_grid);
            not.setImageResource(R.drawable.locked_grid);
            nor.setImageResource(R.drawable.locked_grid);
            xnor.setImageResource(R.drawable.locked_grid);
        }else if(levelNum == 6){
            and.setImageResource(R.drawable.locked_grid);
            or.setImageResource(R.drawable.locked_grid);
        }

    }
}
