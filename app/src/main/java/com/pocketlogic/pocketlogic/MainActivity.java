package com.pocketlogic.pocketlogic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.VideoView;
import java.util.ArrayList;

import com.pocketlogic.pocketlogic.PocketLogic.*;

public class MainActivity extends AppCompatActivity{

    String choosenLevel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView videoview = findViewById(R.id.videoViewRelative);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bgdemo);
        videoview.setVideoURI(uri);
        videoview.start();

        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.levelSpinner);

        LevelManager levelManager = new LevelManager();

        ArrayList<String> levelNames = levelManager.getLevelNames();

        ArrayAdapter<String> adp = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,levelNames);
        spinner.setAdapter(adp);

        spinner.setVisibility(View.VISIBLE);
        //Set listener Called when the item is selected in spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long arg3)
            {
                choosenLevel = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub
            }
        });

    }

    public void buttonClick(View view){
        Intent intent = new Intent(this, GameScene.class);
        intent.putExtra("levelName", choosenLevel);
        startActivity(intent);
    }
}
