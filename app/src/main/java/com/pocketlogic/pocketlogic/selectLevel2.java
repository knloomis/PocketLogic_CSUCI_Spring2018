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

public class selectLevel2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListeners();
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
                finish();
            }
        });
    }
}
