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

public class winScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_screen);
        addListeners();
    }

    public void addListeners() {
        final Context context = this;

        Button btn_main= (Button) findViewById(R.id.btn_win_main);
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, selectLevel.class);
                startActivity(intent);
            }
        });

        Button btn_next= (Button) findViewById(R.id.btn_win_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, selectLevel2.class);
                startActivity(intent);
            }
        });
    }
}
