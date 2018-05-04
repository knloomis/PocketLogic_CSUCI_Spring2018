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

import com.pocketlogic.pocketlogic.PocketLogic.LevelManager;

public class selectLevel extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Log.d("mine","selectLevel Triggered");

        //IMPORTANT!
        //A lot of function is requiring context, hence I am caching it locally.
        this.context = this;
        addListeners();
    }

    public void addListeners() {
        final Context context = this;

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

        Button btn_scores= (Button) this.findViewById(R.id.btn_main_scores);
        btn_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, highscores.class);
                startActivity(intent);
            }
        });

        Button btn_help= (Button) this.findViewById(R.id.btn_main_help);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, help.class);
                startActivity(intent);
            }
        });

    }
}
