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

public class highscores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores_screen);
        addListeners();
    }

    public void addListeners() {
        final Context context = this;

        Button button= (Button) findViewById(R.id.btn_highscores_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(context, selectLevel.class);
               // startActivity(intent);
                finish();
            }
        });
    }
}
