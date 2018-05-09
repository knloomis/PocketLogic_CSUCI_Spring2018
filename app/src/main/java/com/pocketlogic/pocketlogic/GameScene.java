package com.pocketlogic.pocketlogic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.pocketlogic.pocketlogic.PocketLogic.*;

public class GameScene extends AppCompatActivity {

        private Director director;
        String levelName;
        int levelNum;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.game);

            Intent intent = getIntent();
            levelName = intent.getExtras().getString("levelName");

            levelNum = stringNumToInt(levelName.split(" ")[1]);

            LevelManager levelManager = new LevelManager();

            director = new Director(this,this);
            director.load(levelManager.getLevel(levelName));

        }

        public void tileClick(View tileImgView) {
            director.tileClick(tileImgView.getTag().toString());
        }

        public void tileSelect(View choiceImgView){
            director.tileSelect(choiceImgView.getTag().toString().split("_")[1]);
        }

        public void switchToggle(View switchClip) {
            director.switchToggle(switchClip.getTag().toString().split("_")[1]);
        }

        public void exitTruthTable(View truthTableClip) {
            director.exitTruthTable();
        }

        public void tabletClick(View tablet){
            director.openTruthTable();
        }

        public void exitBombSquad(View bombSquadRetImg) {
            director.exitBombSquadPrompt();
        }

        public void exitGame(View view){
     //Toast.makeText(this, "levelNum: " + levelNum, Toast.LENGTH_SHORT).show();
            String gameResult = director.getGameResult(); //Win or Lose
            int nextLevelNum;
            if(gameResult.equals("Win")){
                nextLevelNum = getNextLevelNum();
            }else{
                nextLevelNum = levelNum;
            }
            Intent intent = new Intent(this, selectLevel.class);
            intent.putExtra("levelNum", nextLevelNum);
            startActivity(intent);
            overridePendingTransition(0,0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }

    public void helpPopup(View tileImgView){
        Intent help = new Intent(this, help.class);
        startActivity(help);
        overridePendingTransition(0,0);
        //help.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    public void restartPopup(View tileImgView){
        Intent restart = new Intent(this, RestartConfirmPopupActivity.class);
        restart.putExtra("levelName", levelName);
        startActivity(restart);
        overridePendingTransition(0,0);
        restart.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    public void quitPopup(View tileImgView){
//TO DO:
//      convert string level name to level num
        Intent quit = new Intent(this, QuitConfirmPopupActivity.class);
        quit.putExtra("levelNum", levelNum);
        startActivity(quit);
        overridePendingTransition(0,0);
        quit.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    int stringNumToInt(String stringNum){
            int num = 0;
            if(stringNum.equals("0")){
                num = 0;
            }else if(stringNum.equals("1")){
                num = 1;
            }else if(stringNum.equals("2")){
                num = 2;
            }else if(stringNum.equals("3")){
                num = 3;
            }else if(stringNum.equals("4")){
                num = 4;
            }else if(stringNum.equals("5")){
                num = 5;
            }else if(stringNum.equals("6")){
                num = 6;
            }else if(stringNum.equals("7")){
                num = 7;
            }else if(stringNum.equals("8")){
                num = 8;
            }else if(stringNum.equals("9")){
                num = 9;
            }else if(stringNum.equals("10")){
                num = 10;
            }

            return num;
    }

    public int getNextLevelNumFromString(String stringNum){
            int num = stringNumToInt(stringNum);
            if(num == 10){
                return 10;
            }else{
                return ++num;
            }
    }

    public int getNextLevelNum(){
            if(levelNum == 10){
                return 10;
            }else{
                return ++levelNum;
            }
    }
}
