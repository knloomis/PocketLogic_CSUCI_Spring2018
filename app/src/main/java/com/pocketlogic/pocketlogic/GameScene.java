package com.pocketlogic.pocketlogic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pocketlogic.pocketlogic.PocketLogic.*;

public class GameScene extends AppCompatActivity {

        private Director director;
        String levelName;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.game);

            Intent intent = getIntent();
            levelName = intent.getExtras().getString("levelName");

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
            String gameResult = director.getGameResult(); //Win or Lose
            Intent intent = new Intent(this, selectLevel.class);
            startActivity(intent);
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
        Intent quit = new Intent(this, QuitConfirmPopupActivity.class);
        startActivity(quit);
        overridePendingTransition(0,0);
        quit.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
}
