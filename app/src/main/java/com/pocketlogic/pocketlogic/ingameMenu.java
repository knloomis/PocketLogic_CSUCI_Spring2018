package com.pocketlogic.pocketlogic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Vitaliy Six on 2018/3/10.
 */

public class ingameMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame_menu);
        addListeners();
    }

    public void addListeners() {
        final Context context = this;

        Button btn_return = (Button) findViewById(R.id.btn_ingame_return);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activeLevel.class);
                startActivity(intent);
            }
        });

        Button btn_help= (Button) findViewById(R.id.btn_ingame_help);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, help.class);
                startActivity(intent);
            }
        });

        Button btn_restart= (Button) findViewById(R.id.btn_ingame_restart);
        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activeLevel.class);
                startActivity(intent);
            }
        });

        Button btn_quit= (Button) findViewById(R.id.btn_ingame_quit);
        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, selectLevel.class);
                startActivity(intent);
            }
        });
    }
}
