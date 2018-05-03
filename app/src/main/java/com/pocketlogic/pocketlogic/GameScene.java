package com.pocketlogic.pocketlogic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pocketlogic.pocketlogic.PocketLogic.*;

public class GameScene extends AppCompatActivity {

        private Director director;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.game);

            Intent intent = getIntent();
            String levelName = intent.getExtras().getString("levelName");

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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
}
